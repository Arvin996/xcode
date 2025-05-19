package cn.xk.xcode.mq.customer;

import cn.xk.xcode.config.RabbitMqConfiguration;
import cn.xk.xcode.handler.MessageHandlerHolder;
import com.rabbitmq.client.Channel;
import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.MqResult;
import com.xxl.mq.client.consumer.annotation.MqConsumer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/3/14 10:45
 * @Version 1.0.0
 * @Description ShieldTaskConsumer
 **/
@Component
public class ShieldTaskConsumer{

    @Resource
    private MessageHandlerHolder messageHandlerHolder;

    @RabbitListener(queues = RabbitMqConfiguration.SHIELD_MESSAGE_QUEUE_NAME)
    public void consumeShieldMessage(Message message, Channel channel) {

    }
}
