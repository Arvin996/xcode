package cn.xk.xcode.service.message;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.enums.MessageSendType;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static cn.xk.xcode.config.GlobalMessageConstants.DEALY_MESSAGE_TASK_NOT_DEFINE_SCHEDULE_TIME;
import static cn.xk.xcode.config.GlobalMessageConstants.DEALY_MESSAGE_TASK_SCHEDULE_TIME_MUST_NOT_BEFORE_NOW;
import static cn.xk.xcode.config.RabbitMqConfiguration.*;

/**
 * @Author xuk
 * @Date 2025/5/19 9:55
 * @Version 1.0.0
 * @Description DelaySendMessageService
 **/
@Service
public class DelaySendMessageService extends AbstractSendMessageService {

    private final RabbitTemplate rabbitTemplate;

    public DelaySendMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public String sendType() {
        return MessageSendType.DELAY.getCode();
    }

    @Override
    public CommonResult<?> dealMessage(MessageTask messageTask) {
        LocalDateTime scheduleTime = messageTask.getScheduleTime();
        if (ObjectUtil.isNull(scheduleTime)) {
            return CommonResult.error(DEALY_MESSAGE_TASK_NOT_DEFINE_SCHEDULE_TIME, null);
        }
        if (scheduleTime.isBefore(LocalDateTime.now())) {
            return CommonResult.error(DEALY_MESSAGE_TASK_SCHEDULE_TIME_MUST_NOT_BEFORE_NOW, null);
        }
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_MESSAGE_BINDING_KEY, JSON.toJSONString(messageTask), message -> {
            // 单位毫秒 计算出现在到明天早上9点的毫秒数
            message.getMessageProperties().setDelay((int) LocalDateTimeUtil.between(LocalDateTime.now(), scheduleTime).toMillis());
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });
        return CommonResult.success("延时消息提交成功");
    }
}
