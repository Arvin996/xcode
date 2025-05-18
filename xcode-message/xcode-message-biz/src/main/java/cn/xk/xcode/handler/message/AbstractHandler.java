package cn.xk.xcode.handler.message;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.balance.core.ILoadBalancer;
import cn.xk.xcode.core.ThreadPoolExecutorHolder;
import cn.xk.xcode.entity.po.*;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.enums.*;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.limit.RateLimiterHolder;
import cn.xk.xcode.service.*;
import cn.xk.xcode.utils.CsvCountUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static cn.xk.xcode.config.GlobalMessageConstants.*;
import static cn.xk.xcode.config.RabbitMqConfiguration.*;
import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT;
import static cn.xk.xcode.entity.def.MessageChannelTableDef.MESSAGE_CHANNEL;
import static cn.xk.xcode.entity.def.MessageTemplateParamsTableDef.MESSAGE_TEMPLATE_PARAMS;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.TOO_MANY_REQUESTS;

/**
 * @Author xuk
 * @Date 2025/3/11 15:40
 * @Version 1.0.0
 * @Description BaseHandler
 **/
@Slf4j
public abstract class AbstractHandler implements IHandler {

    private final static int WAIT_TIME = 3;

    @Resource
    private SensitiveWordBs sensitiveWordBs;

    @Resource
    private MessageChannelService messageChannelService;

    @Resource
    private MessageChannelAccountService messageChannelAccountService;

    @Resource
    private MessageTemplateService messageTemplateService;

    @Resource
    private MessageTemplateParamsService messageTemplateParamsService;

    @Resource
    private MessageChannelParamService messageChannelParamService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ILoadBalancer loadBalancer;

    @Resource
    private StringRedisTemplate redisTemplate;

    @Resource
    private RateLimiterHolder rateLimiterHolder;

    @Resource
    private MessageTaskDetailService messageTaskDetailService;

    @Resource
    private MessageTaskService messageTaskService;

    @Resource
    private ThreadPoolExecutorHolder threadPoolExecutorHolder;

    private Map<String, Object> channelAccountParamsValueMap;

    @Resource
    protected StringRedisTemplate stringRedisTemplate;

    @Getter
    private String realMessageCount;

    @Override
    public void sendMessage(MessageTask messageTask) {
        // 1. 敏感词过滤
        String content = getRealMessageContent(messageTask);
        if (sensitiveWordBs.contains(content)) {
            ExceptionUtil.castServerException(MESSAGE_CONTENT_CONTAINS_SENSITIVE_WORDS);
        }
        realMessageCount = content;
        convertReceivers(messageTask);
        // 2. 屏蔽
        handleShield(messageTask);
        // 3. 负载均衡
        MessageChannelAccountPo messageChannelAccountPo = handleLoadBalance(messageTask);
        if (Objects.isNull(messageChannelAccountPo)) {
            ExceptionUtil.castServerException(MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS, messageTask.getSendType());
        }
        // 4. 限流
        if (needRateLimit()) {
            if (!rateLimiterHolder.getRateLimiter().rateLimit(messageTask)) {
                ExceptionUtil.castServerException(TOO_MANY_REQUESTS);
            }
        }
        // 5. 验证参数
        validateChannelAccountParamValue(getChannelAccountParamsValue(messageChannelAccountPo));
        MessageTaskPo messageTaskPo = new MessageTaskPo();
        BeanUtil.copyProperties(messageTask, messageTaskPo);
        messageTaskPo.setAccountId(messageChannelAccountPo.getId());
        messageTaskService.save(messageTaskPo);
        messageTask.setId(messageTaskPo.getId());
        // 5. 发消息
        HandlerResult result = null;
        try {
            result = doSendMessage(messageTask, messageChannelAccountPo);
        } catch (InterruptedException e) {
            log.error("发送消息超时,{}", e.getMessage());
        }
        if (Objects.isNull(result)) {
            return;
        }
        List<MessageTaskDetailPo> list = result.getList();
        Integer successCount = result.getSuccessCount();
        if (successCount == messageTask.getReceiverSet().size() && successCount != 0) {
            messageTaskPo.setStatus(MessageTaskStatusEnum.ALL_SUCCESS.getStatus());
        } else if (successCount == 0) {
            messageTaskPo.setStatus(MessageTaskStatusEnum.FAIL.getStatus());
        } else {
            messageTaskPo.setStatus(MessageTaskStatusEnum.PART_SUCCESS.getStatus());
        }
        messageTaskService.updateById(messageTaskPo);
        // 失败重试
        if (CollectionUtil.isNotEmpty(list)) {
            saveBatchTaskMessageDetails(list);
        }
    }

    private void saveBatchTaskMessageDetails(List<MessageTaskDetailPo> list) {
        messageTaskDetailService.saveBatch(list);
    }

    public abstract void validateChannelAccountParamValue(Map<String, Object> channelAccountParamsValueMap);


    private void convertReceivers(MessageTask messageTask) {
        String receiverType = messageTask.getReceiverType();
        if (ReceiverTypeEnum.isCsv(receiverType)) {
            Set<String> set = new HashSet<>();
            // todo 对接minio 上传csv文件
            CsvCountUtil.handleCsv(messageTask.getReceivers(), row -> {
                set.add(row.get(0));
            });
            messageTask.setReceiverSet(set);
        } else {
            List<String> split = StrUtil.splitTrim(messageTask.getReceivers(), ",");
            messageTask.setReceiverSet(CollectionUtil.convertSet(split, Function.identity()));
        }
    }

    public HandlerResult doSendMessage(MessageTask messageTask, MessageChannelAccountPo messageChannelAccountPo) throws InterruptedException {
        HandlerResult handlerResult = new HandlerResult();
        initChannelSendClient();
        ExecutorService executorService = threadPoolExecutorHolder.routeThreadPool(channelCode());
        Set<String> receiverSet = messageTask.getReceiverSet();
        CountDownLatch countDownLatch = new CountDownLatch(receiverSet.size());
        CopyOnWriteArrayList<SingeSendMessageResult> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        receiverSet.forEach(receiver -> {
            try {
                executorService.execute(() -> {
                    //  这里执行真正发消息的逻辑
                    SingeSendMessageResult singeSendMessageResult = doSendMessage(receiver, messageTask, messageChannelAccountPo);
                    copyOnWriteArrayList.add(singeSendMessageResult);
                });
            } catch (Exception e) {
                log.error("发送消息失败,{}", e.getMessage());
            } finally {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await(WAIT_TIME, TimeUnit.MINUTES);
        int successCount = 0;
        List<MessageTaskDetailPo> list = new ArrayList<>();
        for (SingeSendMessageResult singeSendMessageResult : copyOnWriteArrayList) {
            MessageTaskDetailPo messageTaskDetailPo = new MessageTaskDetailPo();
            messageTaskDetailPo.setTaskId(messageTask.getId());
            messageTaskDetailPo.setReceiver(singeSendMessageResult.getReceiver());
            messageTaskDetailPo.setExecTime(singeSendMessageResult.getExecTime());
            messageTaskDetailPo.setRetryTimes(0);
            if (singeSendMessageResult.isSuccess()) {
                successCount++;
                messageTaskDetailPo.setStatus("0");
                messageTaskDetailPo.setFailMsg(singeSendMessageResult.getFailMsg());
            } else {
                messageTaskDetailPo.setStatus("1");
                messageTaskDetailPo.setSuccessTime(singeSendMessageResult.getSuccessTime());
            }
            list.add(messageTaskDetailPo);

        }
        handlerResult.setSuccessCount(successCount);
        handlerResult.setList(list);
        return handlerResult;
    }

    protected abstract void initChannelSendClient();

    protected abstract SingeSendMessageResult doSendMessage(String receiver, MessageTask messageTask, MessageChannelAccountPo messageChannelAccountPo);

    public MessageChannelAccountPo handleLoadBalance(MessageTask messageTask) {
        MessageChannelPo messageChannelPo = messageChannelService.getOne(MESSAGE_CHANNEL.CODE.eq(messageTask.getSendType()));
        if (Objects.isNull(messageChannelPo)) {
            ExceptionUtil.castServerException(CHANNEL_NOT_EXISTS);
        }
        String supportLoadBalance = messageChannelPo.getSupportLoadBalance();
        if ("1".equals(supportLoadBalance)) {
            // 不支持负载 必然有对应的id
            Integer accountId = messageTask.getAccountId();
            if (ObjectUtil.isNull(accountId)) {
                ExceptionUtil.castServiceException(MESSAGE_TASK_ACCOUNT_NOT_DEFINED);
            }
            return messageChannelAccountService.getById(accountId);
        } else {
            // 支持负载均衡
            List<MessageChannelAccountPo> messageChannelAccountPoList = messageChannelAccountService.list(MESSAGE_CHANNEL_ACCOUNT.CHANNEL_ID.eq(messageChannelPo.getCode()));
            MessageChannelAccountPo channelAccountPo = loadBalancer.choose(messageChannelAccountPoList);
            if (Objects.nonNull(channelAccountPo)) {
                messageTask.setClientId(channelAccountPo.getId());
            }
            return channelAccountPo;
        }
    }

    private void handleShield(MessageTask messageTask) {
        String msgType = messageTask.getMsgType();
        // 延时和定时不能被屏蔽
        if (msgType.equals(MessageSendType.DELAY.getCode()) || msgType.equals(MessageSendType.CORN.getCode())) {
            return;
        }
        String shieldType = messageTask.getShieldType();
        LocalDateTime now = LocalDateTime.now();
        if (shieldType.equals(ShieldType.NIGHT_NO_SHIELD.getCode())) {
            return;
        }
        // 这里是开始生产者的发布确认机制 服务端给的回调 比较损耗性能
        CorrelationData cd = new CorrelationData();
        // 2.给Future添加ConfirmCallback
        cd.getFuture().addCallback(new ListenableFutureCallback<CorrelationData.Confirm>() {
            @Override
            public void onFailure(@NotNull Throwable ex) {
                // 2.1.Future发生异常时的处理逻辑，基本不会触发
                log.error("send message fail", ex);
            }

            @Override
            public void onSuccess(CorrelationData.Confirm result) {
                // 2.2.Future接收到回执的处理逻辑，参数中的result就是回执内容
                if (result.isAck()) { // result.isAck()，boolean类型，true代表ack回执，false 代表 nack回执
                    log.debug("发送消息成功，收到 ack!");
                } else { // result.getReason()，String类型，返回nack时的异常描述
                    log.error("发送消息失败，收到 nack, reason : {}", result.getReason());
                    // 这里可以开启mq自动重试 或者使用定时任务定时扫描
                }
            }
        });
        if (shieldType.equals(ShieldType.NIGHT_SHIELD.getCode())) {
            // 夜间屏蔽 2100-0700 明日9点发送
            if (now.getHour() >= 21 || now.getHour() < 7) {
                // 明日9点
                // 1.创建CorrelationData
                LocalDateTime tomorrow9 = now.plusDays(1).withHour(9).withMinute(0).withSecond(0);
                rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, SHIELD_MESSAGE_BINDING_KEY, JSON.toJSONString(messageTask), message -> {
                    // 单位毫秒 计算出现在到明天早上9点的毫秒数
                    message.getMessageProperties().setDelay((int) LocalDateTimeUtil.between(LocalDateTime.now(), tomorrow9).toMillis());
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                }, cd);
                //   xxlMqTemplate.sendMessage(MqMessageTopicEnum.SHIELD_TOPIC.getTopic(), JSON.toJSONString(messageTask), Date.from(tomorrow9.atZone(ZoneId.systemDefault()).toInstant()));
            }
            return;
        }
        if (shieldType.equals(ShieldType.SHIELD_AT_BETWEEN_TIME.getCode())) {
            String shieldEndTime = messageTask.getShieldEndTime();
            String shieldStartTime = messageTask.getShieldStartTime();
            String format = LocalDateTimeUtil.format(now, "HHmm");
            if (format.compareTo(shieldStartTime) >= 0 && format.compareTo(shieldEndTime) <= 0) {
                if (shieldEndTime.compareTo(shieldStartTime) < 0) {
                    // 跨天了
                    LocalDateTime tomorrow9 = now.plusDays(1).withHour(9).withMinute(Integer.parseInt(shieldEndTime.substring(0, 2))).withSecond(Integer.parseInt(shieldEndTime.substring(2, 4)));
                    rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, SHIELD_MESSAGE_BINDING_KEY, JSON.toJSONString(messageTask), message -> {
                        // 单位毫秒 计算出现在到明天早上9点的毫秒数
                        message.getMessageProperties().setDelay((int) LocalDateTimeUtil.between(LocalDateTime.now(), tomorrow9).toMillis());
                        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        //   message.getMessageProperties().setDeliveryTag();
                        //   message.getMessageProperties().setMessageId();
                        return message;
                    }, cd);
                    // xxlMqTemplate.sendMessage(MqMessageTopicEnum.SHIELD_TOPIC.getTopic(), JSON.toJSONString(messageTask), Date.from(tomorrow9.atZone(ZoneId.systemDefault()).toInstant()));
                } else {
                    // 没有跨天
                    LocalDateTime tomorrow9 = now.withHour(Integer.parseInt(shieldEndTime.substring(0, 2))).withMinute(Integer.parseInt(shieldEndTime.substring(2, 4))).withSecond(0);
                    rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, SHIELD_MESSAGE_BINDING_KEY, JSON.toJSONString(messageTask), message -> {
                        // 单位毫秒 计算出现在到明天早上9点的毫秒数
                        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        message.getMessageProperties().setDelay((int) LocalDateTimeUtil.between(LocalDateTime.now(), tomorrow9).toMillis());
                        return message;
                    }, cd);
                    // xxlMqTemplate.sendMessage(MqMessageTopicEnum.SHIELD_TOPIC.getTopic(), JSON.toJSONString(messageTask), Date.from(tomorrow9.atZone(ZoneId.systemDefault()).toInstant()));
                }
            }
        }
    }

    private String getRealMessageContent(@NotNull MessageTask messageTask) {
        String msgContentType = messageTask.getMsgContentType();
        if (MessageContentType.PLAIN.getCode().equals(msgContentType)) {
            return messageTask.getMessageContent();
        } else {
            MessageTemplatePo messageTemplatePo = messageTemplateService.getById(messageTask.getTemplateId());
            if (Objects.isNull(messageTemplatePo)) {
                ExceptionUtil.castServerException(MESSAGE_TEMPLATE_NOT_EXISTS, messageTask.getTemplateId());
            }
            messageTask.setThirdTemplateId(messageTemplatePo.getTemplateId());
            String content = messageTemplatePo.getContent();
            if (!StringUtils.hasLength(content)) {
                ExceptionUtil.castServerException(MESSAGE_TEMPLATE_CONTENT_NOT_DEFINED, messageTask.getTemplateId());
            }
            List<MessageTemplateParamsPo> templateParamsPoList = messageTemplateParamsService.list(MESSAGE_TEMPLATE_PARAMS.TEMPLATE_ID.eq(messageTask.getTemplateId()));
            String contentValueParams = messageTask.getContentValueParams();
            if (CollectionUtil.isEmpty(templateParamsPoList)) {
                return content;
            }
            JSONObject jsonObject = JSON.parseObject(contentValueParams);
            if (jsonObject.size() != templateParamsPoList.size()) {
                ExceptionUtil.castServerException(MESSAGE_TEMPLATE_PARAMS_NOT_MATCH);
            }
            return formatContent(content, templateParamsPoList, jsonObject);
        }
    }

    private String formatContent(@NotNull String content, List<MessageTemplateParamsPo> templateParamsPoList, JSONObject jsonObject) {
        Set<String> params = CollectionUtil.convertSet(templateParamsPoList, MessageTemplateParamsPo::getName);
        int length = content.length();
        StringBuilder stringBuilder = new StringBuilder();
        int start = 0;
        int end;
        char c;
        boolean flag = false;
        for (int i = 0; i < length; i++) {
            c = content.charAt(i);
            if (c == '{') {
                flag = true;
                start = i;
            } else if (c == '}') {
                flag = false;
                end = i;
                String key = content.substring(start + 1, end);
                if (!params.contains(key)) {
                    ExceptionUtil.castServerException(MESSAGE_TEMPLATE_CONFIG_ERROR, key);
                }
                String value = jsonObject.getString(key);
                if (!StringUtils.hasLength(value)) {
                    ExceptionUtil.castServerException(MESSAGE_TEMPLATE_PARAMS_VALUE_NOT_DEFINED, key);
                }
                stringBuilder.append(value);
            } else {
                if (!flag) {
                    stringBuilder.append(c);
                }
            }
        }
        return stringBuilder.toString();
    }


    protected void saveWithDrawInfo(Integer taskDetailId, String taskId) {
        redisTemplate.opsForValue().set(channelCode() + "_" + taskDetailId, taskId, 1, TimeUnit.DAYS);
    }

    protected String getWithDrawInfo(Integer taskDetailId) {
        return redisTemplate.opsForValue().get(channelCode() + "_" + taskDetailId);
    }

    @Override
    public void withDrawMessage(List<Integer> messageTaskDetailIds) {
        List<WithdrawResult> withdrawResults = doWithDrawMessage(messageTaskDetailIds);
        if (CollectionUtil.isNotEmpty(withdrawResults)) {
            List<MessageTaskDetailPo> list = new ArrayList<>();
            for (WithdrawResult withdrawResult : withdrawResults) {
                MessageTaskDetailPo messageTaskDetailPo = messageTaskDetailService.getById(withdrawResult.getTaskDetailId());
                if (withdrawResult.isSuccess()) {
                    messageTaskDetailPo.setStatus("2");
                    list.add(messageTaskDetailPo);
                } else {
                    // 重试
                    messageTaskDetailPo.setStatus("3");
                }
            }
            if (CollectionUtil.isNotEmpty(list)) {
                saveBatchTaskMessageDetails(list);
            }
        }
    }

    @Override
    public List<WithdrawResult> doWithDrawMessage(List<Integer> messageTaskDetailIds) {
        throw new UnsupportedOperationException("渠道" + channelCode() + "不支持撤回");
    }

    private Map<String, Object> getChannelAccountParamsValue(MessageChannelAccountPo messageChannelAccountPo) {
        channelAccountParamsValueMap = messageChannelParamService.getChannelParamsAndValueForAccount(messageChannelAccountPo.getId());
        return channelAccountParamsValueMap;
    }

    protected Map<String, Object> getChannelAccountParamsValue() {
        return channelAccountParamsValueMap;
    }

}
