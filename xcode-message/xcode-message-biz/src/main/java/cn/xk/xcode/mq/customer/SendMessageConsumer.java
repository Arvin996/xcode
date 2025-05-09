package cn.xk.xcode.mq.customer;

import cn.xk.xcode.config.RabbitMqConfiguration;
import cn.xk.xcode.entity.task.MessageTask;
import cn.xk.xcode.service.consume.ConsumeMessageService;
import com.alibaba.fastjson2.JSON;
import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.MqResult;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/3/14 11:36
 * @Version 1.0.0
 * @Description SendMessageConsumer
 **/
@Component
public class SendMessageConsumer{

    @Resource
    private ConsumeMessageService consumeMessageService;

    @RabbitListener(queues = RabbitMqConfiguration.SEND_MESSAGE_QUEUE_NAME)
    public void consumeSendMessage(String s) {
        MessageTask messageTask = JSON.parseObject(s, MessageTask.class);
        consumeMessageService.consumeSendMessage(messageTask);
    }
}
