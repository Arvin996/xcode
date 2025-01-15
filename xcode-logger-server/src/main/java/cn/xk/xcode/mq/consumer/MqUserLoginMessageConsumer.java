package cn.xk.xcode.mq.consumer;

import cn.xk.xcode.consumer.AbstractEnhanceMessageConsumer;
import cn.xk.xcode.entity.UserLoginLog;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author Administrator
 * @Date 2024/8/26 18:50
 * @Version V1.0.0
 * @Description MqUserLoginMessageConsumer
 */
@RocketMQMessageListener(consumerGroup = "xcode-log-all-group", topic = "gwLog")
@Component
public class MqUserLoginMessageConsumer extends AbstractEnhanceMessageConsumer<UserLoginLog> implements RocketMQListener<UserLoginLog> {

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

    @Override
    public String getThisConsumerInstanceName() {
        return "MqUserLoginMessageConsumer";
    }


    @Override
    public void onMessage(UserLoginLog userLoginLog) {
        dispatchMessage(userLoginLog);
    }
}
