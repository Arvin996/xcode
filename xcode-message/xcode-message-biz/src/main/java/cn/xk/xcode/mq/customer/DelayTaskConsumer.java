package cn.xk.xcode.mq.customer;

import cn.xk.xcode.config.RabbitMqConfiguration;
import cn.xk.xcode.entity.discard.task.MessageTask;
import com.alibaba.fastjson2.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Author xuk
 * @Date 2025/3/14 11:11
 * @Version 1.0.0
 * @Description DelayTaskConsumer
 **/
@Component
@Slf4j
public class DelayTaskConsumer {

    @RabbitListener(queues = RabbitMqConfiguration.DELAY_MESSAGE_QUEUE_NAME)
    public void consumeDelayMessage(Message message, Channel channel) throws IOException {
        log.info("receive delay message:{}", message);
        String body = new String(message.getBody(), StandardCharsets.UTF_8);
        MessageTask messageTask = JSON.parseObject(body, MessageTask.class);
        // 处理消息
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        channel.basicNack(deliveryTag, false, false);
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }
}
