package cn.xk.xcode.mq.customer;

import cn.xk.xcode.config.RabbitMqConfiguration;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author xuk
 * @Date 2025/3/14 11:11
 * @Version 1.0.0
 * @Description DelayTaskConsumer
 **/
@Component
public class DelayTaskConsumer  {

    @RabbitListener(queues = RabbitMqConfiguration.DELAY_MESSAGE_QUEUE_NAME)
    public void consumeDelayMessage(Message message, Channel channel) throws IOException {
        System.out.println("receive message:" + message);
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        channel.basicNack(deliveryTag, false, false);
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }
}
