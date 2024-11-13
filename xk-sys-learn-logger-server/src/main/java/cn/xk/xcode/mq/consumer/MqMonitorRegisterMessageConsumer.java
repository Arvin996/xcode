package cn.xk.xcode.mq.consumer;

import cn.xk.xcode.config.MonitorClientStarter;
import cn.xk.xcode.consumer.AbstractEnhanceMessageConsumer;
import cn.xk.xcode.context.RegisterMonitorServiceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/11/13 14:01
 * @Version 1.0.0
 * @Description MqMonitorRegisterMessageConsumer
 **/
@Slf4j
@RocketMQMessageListener(consumerGroup = "gwLogConsumerGroup", topic = "monitor")
@Component
public class MqMonitorRegisterMessageConsumer extends AbstractEnhanceMessageConsumer<MonitorClientStarter.MonitorMessage> {

    @Override
    protected void handleMessage(MonitorClientStarter.MonitorMessage message) throws Exception {
        // todo
        if (RegisterMonitorServiceContext.isContains(message.getApplicationName())) {
            log.warn("监控服务{} 已存在", message.getApplicationName());
        }
        RegisterMonitorServiceContext.put(message.getApplicationName(), message.getActuatorPath());
    }

    @Override
    protected void handleMaxRetriesExceeded(MonitorClientStarter.MonitorMessage message) {
        log.warn("监控服务{} 注册失败", message.getApplicationName());
    }

    @Override
    protected boolean isRetry() {
        return true;
    }

    @Override
    protected boolean throwException() {
        return false;
    }
}
