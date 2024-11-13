package cn.xk.xcode.core;

import cn.hutool.json.JSONUtil;
import cn.xk.xcode.config.RocketEnhanceProperties;
import cn.xk.xcode.message.BaseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @Author xuk
 * @Date 2024/7/3 09:16
 * @Version 1.0
 * @Description RocketMQEnhanceTemplate 增强工具类
 */
@Slf4j
@RequiredArgsConstructor
public class RocketMQEnhanceTemplate {

    private final RocketMQTemplate rocketMQTemplate;

    private final RocketEnhanceProperties rocketEnhanceProperties;

    public RocketMQTemplate getTemplate() {
        return rocketMQTemplate;
    }

    // 根据环境变量如test dev prod重建topic,如果为false则不指定否则如果为false的时候 topic也会带上enc
    private String rebuildTopic(String topic) {
        return rocketEnhanceProperties.isEnabledIsolation() ? topic + "_"
                + rocketEnhanceProperties.getEnv() : topic;
    }

    private String buildDestination(String topic, String tag) {
        return !StringUtils.isEmpty(tag) ? rebuildTopic(topic) + ":" + tag : rebuildTopic(topic);
    }

    /**
     * 发送同步消息
     */
    public <T extends BaseMessage> SendResult sendSync(String topic, String tag, T message) {
        // 注意分隔符
        Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getBizKey()).build();
        SendResult sendResult = rocketMQTemplate.syncSend(buildDestination(topic, tag), sendMessage);
        log.info("[{}]同步消息[{}]发送结果[{}]", buildDestination(topic, tag), JSONUtil.toJsonStr(message), JSONUtil.toJsonStr(sendResult));
        return sendResult;
    }

    // 发送同步消息
    public <T extends BaseMessage> SendResult sendSync(String topic, T message) {
        return sendSync(topic, null, message);
    }

    // 发送异步消息
    public <T extends BaseMessage> void sendAsync(String topic, String tag, T message, SendCallback sendCallback) {
        Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getBizKey()).build();
        rocketMQTemplate.asyncSend(buildDestination(topic, tag), sendMessage, sendCallback);
    }

    public <T extends BaseMessage> void sendAsync(String topic, T message) {
        sendAsync(topic, null, message, null);
    }

    public <T extends BaseMessage> void sendAsync(String topic, T message, SendCallback sendCallback) {
        sendAsync(topic, null, message, sendCallback);
    }

    public <T extends BaseMessage> void sendAsync(String topic, String tag, T message) {
        sendAsync(topic, tag, message, null);
    }

    // 发送延时消息
    public <T extends BaseMessage> SendResult sendDelay(String topic, String tag, T message, int delayTimeLevel) {
        Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getBizKey()).build();
        SendResult sendResult = rocketMQTemplate.syncSend(buildDestination(topic, tag), sendMessage, delayTimeLevel);
        log.info("[{}]延时消息[{}]发送结果[{}]", buildDestination(topic, tag), JSONUtil.toJsonStr(message), JSONUtil.toJsonStr(sendResult));
        return sendResult;
    }

    /**
     * 发送延迟消息
     */
    public <T extends BaseMessage> SendResult sendDelay(String topic, T message, int delayLevel) {
        return sendDelay(topic, null,  message, delayLevel);
    }
}
