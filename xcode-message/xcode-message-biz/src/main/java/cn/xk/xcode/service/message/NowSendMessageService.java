package cn.xk.xcode.service.message;

import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.enums.MessageSendType;
import cn.xk.xcode.handler.MessageHandlerHolder;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.*;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * @Author xuk
 * @Date 2025/5/19 9:47
 * @Version 1.0.0
 * @Description NowSendMessageService
 **/
@Service
public class NowSendMessageService extends AbstractSendMessageService {

    private final MessageHandlerHolder messageHandlerHolder;

    public NowSendMessageService(MessageHandlerHolder messageHandlerHolder, SensitiveWordBs sensitiveWordBs, RabbitTemplate rabbitTemplate, MessageTemplateService messageTemplateService) {
        this.messageHandlerHolder = messageHandlerHolder;
    }

    @Override
    public String sendType() {
        return MessageSendType.NOW.getCode();
    }

    @Override
    public CommonResult<?> dealMessage(MessageTask messageTask) {
        return messageHandlerHolder.routeHandler(messageTask.getMsgChannel()).sendMessage(messageTask);
    }
}
