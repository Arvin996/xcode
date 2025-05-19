package cn.xk.xcode.service.message;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.enums.MessageSendType;
import cn.xk.xcode.handler.MessageHandlerHolder;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.*;
import cn.xk.xcode.utils.object.BeanUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
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

    private final MessageTaskService messageTaskService;

    public DelaySendMessageService(MessageHandlerHolder messageHandlerHolder, SensitiveWordBs sensitiveWordBs, RabbitTemplate rabbitTemplate, MessageTemplateService messageTemplateService,
                                   MessageTemplateParamsService messageTemplateParamsService,
                                   MessageChannelService messageChannelService,
                                   MessageTaskService messageTaskService,
                                   MessageChannelAccessClientService messageChannelAccessClientService,
                                   MessageChannelAccountService messageChannelAccountService) {
        super(messageHandlerHolder, sensitiveWordBs, rabbitTemplate, messageTemplateService, messageTemplateParamsService, messageChannelService, messageChannelAccessClientService, messageChannelAccountService);
        this.messageTaskService = messageTaskService;
    }

    @Override
    public String sendType() {
        return MessageSendType.NOW.getCode();
    }

    @Override
    public CommonResult<?> dealMessage(MessageTask messageTask) {
        LocalDateTime scheduleTime = messageTask.getScheduleTime();
        if (ObjectUtil.isNull(scheduleTime)) {
            return CommonResult.error(DEALY_MESSAGE_TASK_NOT_DEFINE_SCHEDULE_TIME);
        }
        if (scheduleTime.isBefore(LocalDateTime.now())) {
            return CommonResult.error(DEALY_MESSAGE_TASK_SCHEDULE_TIME_MUST_NOT_BEFORE_NOW);
        }
        MessageTaskPo messageTaskPo = BeanUtil.toBean(messageTask, MessageTaskPo.class);
        messageTaskService.save(messageTaskPo);
        messageTask.setId(messageTaskPo.getId());
        rabbitTemplate.convertAndSend(DELAY_EXCHANGE_NAME, DELAY_MESSAGE_BINDING_KEY, JSON.toJSONString(messageTask), message -> {
            // 单位毫秒 计算出现在到明天早上9点的毫秒数
            message.getMessageProperties().setDelay((int) LocalDateTimeUtil.between(LocalDateTime.now(), scheduleTime).toMillis());
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });
        return CommonResult.success("延时消息提交成功");
    }
}
