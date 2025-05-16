package cn.xk.xcode.service.message;

import cn.xk.xcode.config.RabbitMqConfiguration;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.handler.MessageHandlerHolder;
import cn.xk.xcode.mq.XxlMqTemplate;
import cn.xk.xcode.service.MessageTaskService;
import com.alibaba.fastjson2.JSON;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/17 16:07
 * @Version 1.0.0
 * @Description MessageServiceImpl
 **/
@Service
public class MessageServiceImpl implements MessageService{

    @Resource
    private XxlMqTemplate xxlMqTemplate;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MessageTaskService messageTaskService;

    @Resource
    private MessageHandlerHolder messageHandlerHolder;

    @Override
    public boolean sendMessage(MessageTask messageTask) {
        // 1. 各种校验参数合法
       // xxlMqTemplate.sendMessage(MqMessageTopicEnum.SEND_MESSAGE_TOPIC.getTopic(), JSON.toJSONString(messageTask));
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.MESSAGE_HANDLER_EXCHANGE_NAME, RabbitMqConfiguration.SEND_MESSAGE_BINDING_KEY, JSON.toJSONString(messageTask),  message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });
        return true;
    }

    @Override
    public boolean withDrawMessage(List<Integer> MessageTaskDetailIds) {
        // 直接处理即可 其实不需要做的那么复杂
        // 1.
        return false;
    }
}
