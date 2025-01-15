package cn.xk.xcode.mq.consumer;

import cn.xk.xcode.consumer.AbstractEnhanceMessageConsumer;
import cn.xk.xcode.entity.TraceRecord;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2025/1/14 10:30
 * @Version 1.0.0
 * @Description MqTraceLogMessageConsumer
 **/
@RocketMQMessageListener(consumerGroup = "xcode-log-all-group", topic = "traceLog")
@Component
public class MqTraceLogMessageConsumer extends AbstractEnhanceMessageConsumer<TraceRecord> implements RocketMQListener<TraceRecord> {
    @Override
    protected void handleMessage(TraceRecord message) throws Exception {
        System.out.println(message);
    }

    @Override
    protected void handleMaxRetriesExceeded(TraceRecord message) {

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
        return "MqTraceLogMessageConsumer";
    }

    @Override
    public void onMessage(TraceRecord traceRecord) {
        dispatchMessage(traceRecord);
    }
}
