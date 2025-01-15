package cn.xk.xcode.mq.consumer;

import cn.xk.xcode.consumer.AbstractEnhanceMessageConsumer;
import cn.xk.xcode.entity.BizAccessLog;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/7/9 13:14
 * @Version 1.0
 * @Description MqBizLogMessageConsumer
 */
@RocketMQMessageListener(consumerGroup = "xcode-log-all-group", topic = "bizLog")
@Component
public class MqBizLogMessageConsumer  extends AbstractEnhanceMessageConsumer<BizAccessLog> implements RocketMQListener<BizAccessLog>{

    @Override
    protected void handleMessage(BizAccessLog message) throws Exception {
        // todo 计入数据库
    }

    @Override
    protected void handleMaxRetriesExceeded(BizAccessLog message) {
        // todo 重试失败了 使用定时任务补偿 为了不每个服务都引入消息服务的包 建议配置多数据源
    }

    @Override
    protected boolean isRetry() {
        return true;
    }

    @Override
    protected boolean throwException() {
        // 如果抛出了异常就不会重试了 看怎么优化
        return false;
    }


    @Override
    public String getThisConsumerInstanceName() {
        return "MqBizLogMessageConsumer";
    }

    @Override
    public void onMessage(BizAccessLog bizAccessLog) {
        dispatchMessage(bizAccessLog);
    }
}
