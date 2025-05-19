package cn.xk.xcode.service.message;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.entity.po.*;
import cn.xk.xcode.enums.*;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.handler.MessageHandlerHolder;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.*;
import cn.xk.xcode.utils.CsvCountUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import static cn.xk.xcode.config.GlobalMessageConstants.*;
import static cn.xk.xcode.config.GlobalMessageConstants.MESSAGE_TEMPLATE_PARAMS_NOT_MATCH;
import static cn.xk.xcode.config.RabbitMqConfiguration.DELAY_EXCHANGE_NAME;
import static cn.xk.xcode.config.RabbitMqConfiguration.SHIELD_MESSAGE_BINDING_KEY;
import static cn.xk.xcode.entity.def.MessageChannelAccessClientTableDef.MESSAGE_CHANNEL_ACCESS_CLIENT;
import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT;
import static cn.xk.xcode.entity.def.MessageChannelTableDef.MESSAGE_CHANNEL;
import static cn.xk.xcode.entity.def.MessageTemplateParamsTableDef.MESSAGE_TEMPLATE_PARAMS;
import static cn.xk.xcode.entity.def.MessageTemplateTableDef.MESSAGE_TEMPLATE;

/**
 * @Author xuk
 * @Date 2025/5/19 9:27
 * @Version 1.0.0
 * @Description AbstractSendMessageService
 **/
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractSendMessageService {

    protected final MessageHandlerHolder messageHandlerHolder;

    private final SensitiveWordBs sensitiveWordBs;

    protected final RabbitTemplate rabbitTemplate;

    protected final MessageTemplateService messageTemplateService;

    protected final MessageTemplateParamsService messageTemplateParamsService;

    protected final MessageChannelService messageChannelService;

    private final MessageChannelAccessClientService messageChannelAccessClientService;

    private final MessageChannelAccountService messageChannelAccountService;

    protected final MessageTaskService messageTaskService;


    public CommonResult<?> sendMessage(MessageTask messageTask) {
        // 铭感词过滤
        filterSensitiveMsgContent(messageTask);
        String msgChannel = messageTask.getMsgChannel();
        MessageChannelPo messageChannelPo = messageChannelService.getOne(MESSAGE_CHANNEL.CODE.eq(msgChannel));
        if (ObjectUtil.isNull(messageChannelPo)) {
            ExceptionUtil.castServiceException(CHANNEL_NOT_EXISTS);
        }
        messageTask.setChannelId(messageChannelPo.getId());
        if (StrUtil.isNotBlank(messageTask.getAccountName())) {
            MessageChannelAccountPo messageChannelAccountPo = messageChannelAccountService.getOne(MESSAGE_CHANNEL_ACCOUNT.ACCOUNT_NAME.eq(messageTask.getAccountName()).and(MESSAGE_CHANNEL_ACCOUNT.CHANNEL_ID.eq(messageChannelPo.getId())));
            if (ObjectUtil.isNull(messageChannelAccountPo)) {
                ExceptionUtil.castServiceException(MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS);
            }
            messageTask.setAccountId(messageChannelAccountPo.getId());
        }
        convertReceivers(messageTask);
        MessageChannelAccessClientPo messageChannelAccessClientPo = messageChannelAccessClientService.getOne(MESSAGE_CHANNEL_ACCESS_CLIENT.ACCESS_TOKEN.eq(messageTask.getClientAccessToken()));
        messageChannelAccessClientService.validateClient(messageChannelAccessClientPo, messageTask.getReceiverSet().size());
        messageTask.setClientId(messageChannelAccessClientPo.getId());
        MessageTaskPo messageTaskPo = BeanUtil.toBean(messageTask, MessageTaskPo.class);
        messageTaskService.save(messageTaskPo);
        messageTask.setId(messageTaskPo.getId());
        // 2. 屏蔽
        boolean b = handleShield(messageTask);
        if (b) {
            // 屏蔽了
            return CommonResult.success("消息已被屏蔽，会在屏蔽时间结束后发送");
        }
        return dealMessage(messageTask);
    }

    public abstract String sendType();

    public abstract CommonResult<?> dealMessage(MessageTask messageTask);

    private void convertReceivers(MessageTask messageTask) {
        String msgChannel = messageTask.getMsgChannel();
        if (MessageChannelEnum.FEI_SHU_ROBOT.getCode().equals(msgChannel)) {
            messageTask.setReceiverSet(CollectionUtil.createSingleSet("飞书机器人"));
            messageTask.setReceivers("飞书机器人");
            return;
        }
        if (MessageChannelEnum.DING_DING_ROBOT.getCode().equals(msgChannel)) {
            messageTask.setReceiverSet(CollectionUtil.createSingleSet("钉钉机器人"));
            messageTask.setReceivers("钉钉机器人");
            return;
        }
        String receiverType = messageTask.getReceiverType();
        if (ReceiverTypeEnum.isCsv(receiverType)) {
            Set<String> set = new HashSet<>();
            // todo 对接minio 上传csv文件
            CsvCountUtil.handleCsv(messageTask.getReceivers(), row -> {
                set.add(row.get(0));
            });
            messageTask.setReceiverSet(set);
            messageTask.setReceivers(StrUtil.join(",", set));
        } else {
            List<String> split = StrUtil.splitTrim(messageTask.getReceivers(), ",");
            messageTask.setReceiverSet(CollectionUtil.convertSet(split, Function.identity()));
        }
    }

    private void filterSensitiveMsgContent(@NotNull MessageTask messageTask) {
        String msgContentType = messageTask.getMsgContentType();
        if (MessageContentType.PLAIN.getCode().equals(msgContentType)) {
            if (StrUtil.isBlank(messageTask.getMessageContent())) {
                ExceptionUtil.castServiceException(PLAIN_TEXT_MESSAGE_MUST_NOT_EMPTY);
            }
            String messageContent = messageTask.getMessageContent();
            if (sensitiveWordBs.contains(messageContent)) {
                ExceptionUtil.castServerException(MESSAGE_CONTENT_CONTAINS_SENSITIVE_WORDS);
            }
        } else {
            MessageTemplatePo messageTemplatePo = messageTemplateService.getOne(MESSAGE_TEMPLATE.TEMPLATE_ID.eq(messageTask.getTemplateId()));
            if (Objects.isNull(messageTemplatePo)) {
                ExceptionUtil.castServerException(MESSAGE_TEMPLATE_NOT_EXISTS, messageTask.getTemplateId());
            }
            String templateType = messageTemplatePo.getType();
            // 自定义模板
            if ("0".equals(templateType)) {
                String content = messageTemplatePo.getContent();
                if (!StringUtils.hasLength(content)) {
                    ExceptionUtil.castServerException(MESSAGE_TEMPLATE_CONTENT_NOT_DEFINED, messageTask.getTemplateId());
                }
                List<MessageTemplateParamsPo> templateParamsPoList = messageTemplateParamsService.list(MESSAGE_TEMPLATE_PARAMS.TEMPLATE_ID.eq(messageTask.getTemplateId()));
                if (CollectionUtil.isEmpty(templateParamsPoList)) {
                    messageTask.setMessageContent(content);
                    return;
                }
                String contentValueParams = messageTask.getContentValueParams();
                JSONObject jsonObject = JSON.parseObject(contentValueParams);
                if (jsonObject.size() != templateParamsPoList.size()) {
                    ExceptionUtil.castServerException(MESSAGE_TEMPLATE_PARAMS_NOT_MATCH);
                }
                String realContent = formatContent(content, templateParamsPoList, jsonObject);
                if (sensitiveWordBs.contains(realContent)) {
                    ExceptionUtil.castServerException(MESSAGE_CONTENT_CONTAINS_SENSITIVE_WORDS);
                }
                messageTask.setMessageContent(realContent);
            } else {
                // 三方模板
                String contentValueParams = messageTask.getContentValueParams();
                if (StrUtil.isNotBlank(contentValueParams)) {
                    if (sensitiveWordBs.contains(contentValueParams)) {
                        ExceptionUtil.castServerException(MESSAGE_CONTENT_CONTAINS_SENSITIVE_WORDS);
                    }
                }
            }
        }
    }

    private String formatContent(@NotNull String content, List<MessageTemplateParamsPo> templateParamsPoList, JSONObject jsonObject) {
        for (MessageTemplateParamsPo messageTemplateParamsPo : templateParamsPoList) {
            String paramName = messageTemplateParamsPo.getName();
            if (jsonObject.containsKey(paramName)) {
                String value = jsonObject.getString(paramName);
                content = content.replace("{" + paramName + "}", value);
            }
        }
        return content;
    }

    private boolean handleShield(MessageTask messageTask) {
        String msgType = messageTask.getMsgType();
        // 延时和定时不能被屏蔽
        if (msgType.equals(MessageSendType.DELAY.getCode()) || msgType.equals(MessageSendType.CORN.getCode())) {
            return false;
        }
        String shieldType = messageTask.getShieldType();
        LocalDateTime now = LocalDateTime.now();
        if (shieldType.equals(ShieldType.NIGHT_NO_SHIELD.getCode())) {
            return false;
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
                } else {
                    // 没有跨天
                    LocalDateTime tomorrow9 = now.withHour(Integer.parseInt(shieldEndTime.substring(0, 2))).withMinute(Integer.parseInt(shieldEndTime.substring(2, 4))).withSecond(0);
                    rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, SHIELD_MESSAGE_BINDING_KEY, JSON.toJSONString(messageTask), message -> {
                        // 单位毫秒 计算出现在到明天早上9点的毫秒数
                        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        message.getMessageProperties().setDelay((int) LocalDateTimeUtil.between(LocalDateTime.now(), tomorrow9).toMillis());
                        return message;
                    }, cd);
                }
            }
        }
        return true;
    }
}
