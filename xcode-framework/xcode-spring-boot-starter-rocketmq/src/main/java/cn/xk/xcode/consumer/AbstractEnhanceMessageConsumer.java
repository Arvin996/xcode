package cn.xk.xcode.consumer;

import cn.hutool.json.JSONUtil;
import cn.xk.xcode.core.RocketMQEnhanceTemplate;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.message.BaseMessage;
import cn.xk.xcode.utils.object.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;

import javax.annotation.Resource;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.MQ_MESSAGE_SEND_FAIL;

/**
 * @Author xuk
 * @Date 2024/7/3 09:44
 * @Version 1.0
 * @Description AbstractEnhanceMessageConsumer 消费者抽取
 */
@Slf4j
@SuppressWarnings("all")
public abstract class AbstractEnhanceMessageConsumer<T extends BaseMessage> implements RocketMQPushConsumerLifecycleListener {

    public static final int MAX_RETRY_TIMES = 5;

    private static final int DELAY_LEVEL = 2;

    private static final String RETRY_PREFIX = "RETRY";

    @Resource
    private RocketMQEnhanceTemplate rocketMQEnhanceTemplate;

    /**
     * 消息处理
     *
     * @param message 待处理消息
     * @throws Exception 消费异常
     */
    protected abstract void handleMessage(T message) throws Exception;

    /**
     * 超过重试次数消息，需要启用isRetry
     *
     * @param message 待处理消息
     */
    protected abstract void handleMaxRetriesExceeded(T message);

    /**
     * 是否需要根据业务规则过滤消息，去重逻辑可以在此处处理
     *
     * @param message 待处理消息
     * @return true: 本次消息被过滤，false：不过滤
     */
    protected boolean filter(T message) {
        return false;
    }


    /**
     * 是否异常时重复发送
     *
     * @return true: 消息重试，false：不重试
     */
    protected abstract boolean isRetry();

    /**
     * 消费异常时是否抛出异常
     * 返回true，则由rocketmq机制自动重试
     * false：消费异常(如果没有开启重试则消息会被自动ack)
     */
    protected abstract boolean throwException();

    /**
     * 最大重试次数
     *
     * @return 最大重试次数，默认5次
     */
    protected int getMaxRetryTimes() {
        return MAX_RETRY_TIMES;
    }

    /**
     * isRetry开启时，重新入队延迟时间
     *
     * @return -1：立即入队重试
     */
    protected int getDelayLevel() {
        return DELAY_LEVEL;
    }

    /**
     * 使用模板模式构建消息消费框架，可自由扩展或删减
     */
    public void dispatchMessage(T message) {
        // 基础日志记录被父类处理了
        log.info("消费者收到消息[{}]", JSON.toJSONString(message));
        if (filter(message)) {
            log.info("消息id{}不满足消费条件，已过滤。", message.getBizKey());
            return;
        }
        // 超过最大重试次数时调用子类方法处理
        if (message.getRetryTimes() > getMaxRetryTimes()) {
            handleMaxRetriesExceeded(message);
            return;
        }
        try {
            long now = System.currentTimeMillis();
            handleMessage(message);
            long costTime = System.currentTimeMillis() - now;
            log.info("消息{}消费成功，耗时[{}ms]", message.getBizKey(), costTime);
        } catch (Exception e) {
            log.error("消息{}消费异常,异常信息{}", message.getBizKey(), e.getMessage());
            // 是捕获异常还是抛出，由子类决定
            if (throwException()) {
                //抛出异常，由DefaultMessageListenerConcurrently类处理
                throw new RuntimeException(e);
            }
            //此时如果不开启重试机制，则默认ACK了
            if (isRetry()) {
                handleRetry(message);
            }
        }
    }

    protected void handleRetry(T message) {
        // 获取子类RocketMQMessageListener注解拿到topic和tag
        RocketMQMessageListener annotation = this.getClass().getAnnotation(RocketMQMessageListener.class);
        if (annotation == null) {
            return;
        }
        BaseMessage baseMessage = (BaseMessage) message;
        //重新构建消息体
        String messageSource = baseMessage.getMessageSource();
        if (!messageSource.startsWith(RETRY_PREFIX)) {
            baseMessage.setMessageSource(RETRY_PREFIX + messageSource);
        }
        baseMessage.setRetryTimes(baseMessage.getRetryTimes() + 1);

        SendResult sendResult;

        try {
            // 如果消息发送不成功，则再次重新发送，如果发送异常则抛出由MQ再次处理(异常时不走延迟消息)
            sendResult = rocketMQEnhanceTemplate.sendDelay(annotation.topic(), annotation.selectorExpression(), baseMessage, getDelayLevel());
        } catch (Exception ex) {
            // 此处捕获之后，相当于此条消息被消息完成然后重新发送新的消息
            //由生产者直接发送
            throw new RuntimeException(ex);
        }
        // 发送失败的处理就是不进行ACK，由RocketMQ重试
        if (sendResult.getSendStatus() != SendStatus.SEND_OK) {
            throw new ServiceException(MQ_MESSAGE_SEND_FAIL);
        }

    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        defaultMQPushConsumer.setInstanceName(getThisConsumerInstanceName());
    }

    public abstract String getThisConsumerInstanceName();

}
