package cn.xk.xcode.mq.consumer;

import cn.xk.xcode.consumer.AbstractEnhanceMessageConsumer;
import cn.xk.xcode.entity.UserLoginLog;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

/**
 * @Author Administrator
 * @Date 2024/8/26 18:50
 * @Version V1.0.0
 * @Description MqUserLoginMessageConsumer
 */
@RocketMQMessageListener(consumerGroup = "userLoginLogConsumerGroup", topic = "gwLog")
@Component
public class MqUserLoginMessageConsumer extends AbstractEnhanceMessageConsumer<UserLoginLog> {

    @Override
    protected void handleMessage(UserLoginLog message) throws Exception {

    }

    @Override
    protected void handleMaxRetriesExceeded(UserLoginLog message) {

    }

    @Override
    protected boolean isRetry() {
        return false;
    }

    @Override
    protected boolean throwException() {
        return false;
    }
}
