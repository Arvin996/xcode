package cn.xk.xcode.mq.customer;

import cn.xk.xcode.config.RabbitMqConfiguration;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2025/4/21 9:25
 * @Version 1.0.0
 * @Description MessageHandlerFailConsumer
 **/
@Component
public class MessageHandlerFailConsumer {

    @RabbitListener(queues = RabbitMqConfiguration.MESSAGE_HANDLE_FAIL_QUEUE_NAME)
    public void consumeMessageHandlerFail(String message) {
        System.out.println("receive message:" + message);
    }
}
