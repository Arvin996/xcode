package cn.xk.xcode.mq.customer;

import cn.xk.xcode.config.RabbitMqConfiguration;
import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.MqResult;
import com.xxl.mq.client.consumer.annotation.MqConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2025/3/14 10:45
 * @Version 1.0.0
 * @Description ShieldTaskConsumer
 **/
@Component
public class ShieldTaskConsumer{

    @RabbitListener(queues = RabbitMqConfiguration.SHIELD_MESSAGE_QUEUE_NAME)
    public void consumeShieldMessage(String message) {
        System.out.println("receive message:" + message);
    }
}
