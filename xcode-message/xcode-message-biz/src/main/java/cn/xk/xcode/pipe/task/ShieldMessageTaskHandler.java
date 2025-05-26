package cn.xk.xcode.pipe.task;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.xk.xcode.core.pipeline.TaskContext;
import cn.xk.xcode.core.pipeline.TaskHandler;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.enums.MessageSendType;
import cn.xk.xcode.enums.ShieldType;
import cn.xk.xcode.pipe.model.SendMessageTaskModel;
import cn.xk.xcode.pojo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.xk.xcode.config.GlobalMessageConstants.*;
import static cn.xk.xcode.config.RabbitMqConfiguration.DELAY_EXCHANGE_NAME;
import static cn.xk.xcode.config.RabbitMqConfiguration.SHIELD_MESSAGE_BINDING_KEY;

/**
 * @Author xuk
 * @Date 2025/5/26 11:37
 * @Version 1.0.0
 * @Description ShieldMessageTaskHandler
 **/
@Component
@Slf4j
public class ShieldMessageTaskHandler implements TaskHandler<SendMessageTaskModel> {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Override
    public void handle(TaskContext<SendMessageTaskModel> taskContext) {
        SendMessageTaskModel taskModel = taskContext.getTaskModel();
        MessageTask messageTask = taskModel.getMessageTask();
        boolean b = handleShield(messageTask);
        if (b) {
            // 屏蔽了
            taskContext.setIsBreak(true);
            taskContext.setResult(CommonResult.success("消息已被屏蔽，会在屏蔽时间结束后发送"));
        }
    }

    @Override
    public int getOrder() {
        return SEND_MESSAGE_TASK_STEP_THREE;
    }

    @Override
    public String getCode() {
        return SEND_MESSAGE_TASK_CODE;
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
                rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, SHIELD_MESSAGE_BINDING_KEY, messageTask, message -> {
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
                    rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, SHIELD_MESSAGE_BINDING_KEY, messageTask, message -> {
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
                    rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, SHIELD_MESSAGE_BINDING_KEY, messageTask, message -> {
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
