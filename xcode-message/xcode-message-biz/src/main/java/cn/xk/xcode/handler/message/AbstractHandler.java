package cn.xk.xcode.handler.message;

import cn.hutool.core.bean.BeanUtil;
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
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static cn.xk.xcode.config.GlobalMessageConstants.*;
import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT;
import static cn.xk.xcode.entity.def.MessageChannelTableDef.MESSAGE_CHANNEL;
import static cn.xk.xcode.entity.def.MessageTaskDetailTableDef.MESSAGE_TASK_DETAIL;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.TOO_MANY_REQUESTS;

/**
 * @Author xuk
 * @Date 2025/3/11 15:40
 * @Version 1.0.0
 * @Description BaseHandler
 **/
@Slf4j
public abstract class AbstractHandler implements IHandler {

    private final static int WAIT_TIME = 30;

    @Resource
    private MessageChannelService messageChannelService;

    @Resource
    private MessageChannelAccountService messageChannelAccountService;


    @Resource
    private MessageChannelParamService messageChannelParamService;

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

    @Resource
    protected MessageChannelAccessClientService messageChannelAccessClientService;

    @Override
    public void sendMessage(MessageTask messageTask) {
        // 1. 负载均衡
        MessageChannelAccountPo messageChannelAccountPo = handleLoadBalance(messageTask);
        if (Objects.isNull(messageChannelAccountPo)) {
            ExceptionUtil.castServerException(MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS, messageTask.getMsgChannel());
        }
        // 2. 限流
        if (needRateLimit()) {
            if (!rateLimiterHolder.getRateLimiter().rateLimit(messageTask)) {
                ExceptionUtil.castServerException(TOO_MANY_REQUESTS);
            }
        }
        // 3. 验证参数
        validateChannelAccountParamValue(getChannelAccountParamsValue(messageChannelAccountPo));
        MessageTaskPo messageTaskPo = messageTaskService.getById(messageTask.getId());
        messageTaskPo.setAccountId(messageChannelAccountPo.getId());
        messageTaskService.updateById(messageTaskPo);
        // 5. 发消息
        HandlerResult result = null;
        try {
            result = doSendMessage(messageTask, messageChannelAccountPo);
        } catch (InterruptedException e) {
            log.error("发送消息超时,{}", e.getMessage());
            ExceptionUtil.castServiceException(SEND_MESSAGE_TASK_TIMEOUT);
        }
        if (Objects.isNull(result)) {
            return;
        }
        List<MessageTaskDetailPo> list = result.getList();
        Integer successCount = result.getSuccessCount();
        MessageChannelAccessClientPo channelAccessClientPo = messageChannelAccessClientService.getById(messageTask.getClientId());
        updateClientAccessCount(channelAccessClientPo, successCount);
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

    @Override
    public void reSendTaskMessage(Long taskId) {
        initChannelSendClient();
        MessageTaskPo messageTaskPo = messageTaskService.getById(taskId);
        if (ObjectUtil.isNull(messageTaskPo)) {
            ExceptionUtil.castServiceException(MESSAGE_TASK_NOT_EXISTS);
        }
        MessageChannelAccessClientPo channelAccessClientPo = messageChannelAccessClientService.getById(messageTaskPo.getClientId());
        MessageChannelAccountPo messageChannelAccountPo = messageChannelAccountService.getById(messageTaskPo.getAccountId());
        if (ObjectUtil.isNull(messageChannelAccountPo)) {
            ExceptionUtil.castServiceException(THE_MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS);
        }
        MessageTask messageTask = BeanUtil.toBean(messageTaskPo, MessageTask.class);
        messageTask.setReceiverSet(new HashSet<>(StrUtil.split(messageTaskPo.getReceivers(), ",")));
        messageChannelAccessClientService.validateClient(channelAccessClientPo, messageTask.getReceiverSet().size());
        HandlerResult result = null;
        try {
            result = doSendMessage(messageTask, messageChannelAccountPo);
        } catch (InterruptedException e) {
            log.error("发送消息超时,{}", e.getMessage());
            ExceptionUtil.castServiceException(SEND_MESSAGE_TASK_TIMEOUT);
        }
        if (Objects.isNull(result)) {
            log.warn("执行结果为空");
            return;
        }
        List<MessageTaskDetailPo> list = result.getList();
        Integer successCount = result.getSuccessCount();
        updateClientAccessCount(channelAccessClientPo, successCount);
        if (successCount == messageTask.getReceiverSet().size() && successCount != 0) {
            messageTaskPo.setStatus(MessageTaskStatusEnum.ALL_SUCCESS.getStatus());
        } else if (successCount == 0) {
            messageTaskPo.setStatus(MessageTaskStatusEnum.FAIL.getStatus());
        } else {
            messageTaskPo.setStatus(MessageTaskStatusEnum.PART_SUCCESS.getStatus());
        }
        if (!(MessageTaskStatusEnum.CANCEL.getStatus().equals(messageTaskPo.getStatus()) ||
                (MessageTaskStatusEnum.PAUSE.getStatus().equals(messageTaskPo.getStatus())))) {
            messageTaskService.updateById(messageTaskPo);
        }
        List<MessageTaskDetailPo> list1 = messageTaskDetailService.list(MESSAGE_TASK_DETAIL.TASK_ID.eq(taskId));
        Map<String, MessageTaskDetailPo> map = new HashMap<>();
        list1.forEach(messageTaskDetailPo -> {
          map.put(messageTaskDetailPo.getReceiver(), messageTaskDetailPo);
        });
        List<MessageTaskDetailPo> list2 = new ArrayList<>();
        for (MessageTaskDetailPo messageTaskDetailPo : list) {
            MessageTaskDetailPo messageTaskDetailPo1 = map.get(messageTaskDetailPo.getReceiver());
            if (ObjectUtil.isNotNull(messageTaskDetailPo1)){
                messageTaskDetailPo1.setStatus(messageTaskDetailPo.getStatus());
                messageTaskDetailPo1.setFailMsg(messageTaskDetailPo.getFailMsg());
                messageTaskDetailPo1.setSuccessTime(messageTaskDetailPo.getSuccessTime());
                messageTaskDetailPo1.setExecTime(messageTaskDetailPo.getExecTime());
                list2.add(messageTaskDetailPo1);
            }
        }
        if (CollectionUtil.isNotEmpty(list2)) {
            messageTaskDetailService.updateBatch(list2);
        }
    }

    private void updateClientAccessCount(MessageChannelAccessClientPo channelAccessClientPo, int successCount){
        channelAccessClientPo.setRestCount(channelAccessClientPo.getRestCount() - successCount);
        channelAccessClientPo.setUsedCount(channelAccessClientPo.getUsedCount() + successCount);
        messageChannelAccessClientService.updateById(channelAccessClientPo);
    }


    @Override
    public void reSendSingleTask(Long taskDetailId) {
        initChannelSendClient();
        MessageTaskDetailPo messageTaskDetailPo = messageTaskDetailService.getById(taskDetailId);
        if (ObjectUtil.isNull(messageTaskDetailPo)) {
            ExceptionUtil.castServiceException(MESSAGE_TASK_DETAIL_NOT_EXISTS);
        }
        messageTaskDetailPo.setExecTime(LocalDateTime.now());
        MessageTaskPo messageTaskPo = messageTaskService.getById(messageTaskDetailPo.getTaskId());
        if (ObjectUtil.isNull(messageTaskPo)) {
            ExceptionUtil.castServiceException(MESSAGE_TASK_NOT_EXISTS);
        }
        MessageChannelAccessClientPo channelAccessClientPo = messageChannelAccessClientService.getById(messageTaskPo.getClientId());
        MessageChannelAccountPo messageChannelAccountPo = messageChannelAccountService.getById(messageTaskPo.getAccountId());
        if (ObjectUtil.isNull(messageChannelAccountPo)) {
            ExceptionUtil.castServiceException(THE_MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS);
        }
        MessageTask messageTask = BeanUtil.toBean(messageTaskPo, MessageTask.class);
        SingeSendMessageResult singeSendMessageResult = doSendMessage(messageTaskDetailPo.getReceiver(), messageTask, messageChannelAccountPo);
        // 1. 更新消息任务详情
        if (singeSendMessageResult.isSuccess()) {
            messageTaskDetailPo.setStatus("0");
            messageTaskDetailPo.setSuccessTime(LocalDateTime.now());
            updateClientAccessCount(channelAccessClientPo, 1);
        } else {
            messageTaskDetailPo.setStatus("1");
            messageTaskDetailPo.setFailMsg(singeSendMessageResult.getFailMsg());
        }
        messageTaskDetailService.updateById(messageTaskDetailPo);
        // 2. 更新消息任务
        long count = messageTaskDetailService.count(MESSAGE_TASK_DETAIL.TASK_ID.eq(messageTaskDetailPo.getTaskId()));
        long successCount = messageTaskDetailService.count(MESSAGE_TASK_DETAIL.TASK_ID.eq(messageTaskDetailPo.getTaskId()).and(MESSAGE_TASK_DETAIL.STATUS.eq("0")));
        if (count == successCount) {
            messageTaskPo.setStatus(MessageTaskStatusEnum.ALL_SUCCESS.getStatus());
        } else {
            if (successCount == 0) {
                messageTaskPo.setStatus(MessageTaskStatusEnum.FAIL.getStatus());
            } else {
                messageTaskPo.setStatus(MessageTaskStatusEnum.PART_SUCCESS.getStatus());
            }
            if (!(MessageTaskStatusEnum.CANCEL.getStatus().equals(messageTaskPo.getStatus()) ||
                    (MessageTaskStatusEnum.PAUSE.getStatus().equals(messageTaskPo.getStatus())))) {
                messageTaskService.updateById(messageTaskPo);
            }
        }
    }

    private void saveBatchTaskMessageDetails(List<MessageTaskDetailPo> list) {
        messageTaskDetailService.saveBatch(list);
    }

    public abstract void validateChannelAccountParamValue(Map<String, Object> channelAccountParamsValueMap);

    @Override
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
        MessageChannelPo messageChannelPo = messageChannelService.getOne(MESSAGE_CHANNEL.CODE.eq(messageTask.getMsgChannel()));
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
