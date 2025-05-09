package cn.xk.xcode.mq.customer;

import cn.xk.xcode.config.RabbitMqConfiguration;
import com.xxl.mq.client.consumer.IMqConsumer;
import com.xxl.mq.client.consumer.MqResult;
import com.xxl.mq.client.consumer.annotation.MqConsumer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/3/14 11:37
 * @Version 1.0.0
 * @Description WithdrawMessageConsumer
 **/
@Component
public class WithdrawMessageConsumer {

    @RabbitListener(queues = RabbitMqConfiguration.WITHDRAW_MESSAGE_QUEUE_NAME)
    public void consumeWithdrawMessage(String message) {
        System.out.println("receive message:" + message);
    }
}
