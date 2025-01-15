package cn.xk.xcode.mq.consumer;

import cn.xk.xcode.consumer.AbstractEnhanceMessageConsumer;
import cn.xk.xcode.entity.BizAccessLog;
import cn.xk.xcode.entity.GwAccessLog;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/7/9 13:14
 * @Version 1.0
 * @Description MqGwLogMessageConsumer
 */
@RocketMQMessageListener(consumerGroup = "xcode-log-all-group", topic = "gwLog")
@Component
public class MqGwLogMessageConsumer extends AbstractEnhanceMessageConsumer<GwAccessLog> implements RocketMQListener<GwAccessLog> {

    @Override
    protected void handleMessage(GwAccessLog message) throws Exception {

    }

    @Override
    protected void handleMaxRetriesExceeded(GwAccessLog message) {

    }

    @Override
    protected boolean isRetry() {
        return true;
    }

    @Override
    protected boolean throwException() {
        return false;
    }

    @Override
    public String getThisConsumerInstanceName() {
        return "MqGwLogMessageConsumer";
    }

    @Override
    public void onMessage(GwAccessLog gwAccessLog) {
        dispatchMessage(gwAccessLog);
    }
}
