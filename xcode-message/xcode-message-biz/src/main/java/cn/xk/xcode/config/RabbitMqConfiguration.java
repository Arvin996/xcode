package cn.xk.xcode.config;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/4/18 9:18
 * @Version 1.0.0
 * @Description RabbitMqConfiguration
 **/
@Configuration
@Slf4j
@AllArgsConstructor
public class RabbitMqConfiguration {

    // 声明bindingKey
    public static final String DELAY_MESSAGE_BINDING_KEY = "delayMessageBindingKey";
    public static final String SHIELD_MESSAGE_BINDING_KEY = "shieldMessageBindingKey";
    public static final String RETRY_MESSAGE_BINDING_KEY = "retryMessageBindingKey";

    // 声明队列名称
    public static final String DELAY_MESSAGE_QUEUE_NAME = "delayMessageQueue";
    public static final String SHIELD_MESSAGE_QUEUE_NAME = "shieldMessageQueue";
    public static final String RETRY_MESSAGE_QUEUE_NAME = "retryMessageQueue";

    // 声明交换机名称
    public static final String DELAY_EXCHANGE_NAME = "delayExchange";
    public static final String RETRY_EXCHANGE_NAME = "retryExchange";

    private final RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        // 设置return callback
        // 当消息发布到mq服务或者路由不到队列 或者没有指定的路由器时触发
        rabbitTemplate.setReturnsCallback(returned -> {
            log.error("触发return callback,");
            log.debug("exchange: {}", returned.getExchange());
            log.debug("routingKey: {}", returned.getRoutingKey());
            log.debug("message: {}", returned.getMessage());
            log.debug("replyCode: {}", returned.getReplyCode());
            log.debug("replyText: {}", returned.getReplyText());
        });
        rabbitTemplate.setMessageConverter(messageConverter(SpringUtil.getBean(ObjectMapper.class)));
    }

    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        // 1.定义消息转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter(objectMapper);
        // 2.配置自动创建消息id，用于识别不同消息，也可以在业务中基于ID判断是否是重复消息
        jackson2JsonMessageConverter.setCreateMessageIds(true);
        return jackson2JsonMessageConverter;
    }

    // 这里用于声明交换机=============================
    // 1. 延时交换机
    @Bean(DELAY_EXCHANGE_NAME)
    public DirectExchange delayExchange() {
        return ExchangeBuilder
                .directExchange(DELAY_EXCHANGE_NAME)
                .delayed()  // 设置延时交换机
                .durable(true)  // 设置持久化
                .build();
    }

    // 2. 重试交换机
    @Bean(RETRY_EXCHANGE_NAME)
    public DirectExchange retryExchange() {
        return ExchangeBuilder
                .directExchange(RETRY_EXCHANGE_NAME)
                .delayed()  // 设置延时交换机
                .durable(true)  // 设置持久化
                .build();
    }

    // 这里用于声明队列==============================
    // 1.1 处理延时消息的队列
    @Bean(DELAY_MESSAGE_QUEUE_NAME)
    public Queue delayMessageQueue() {
        return QueueBuilder.durable(DELAY_MESSAGE_QUEUE_NAME).lazy().build();
    }

    // 1.2 处理屏蔽消息第二天发送的延时队列
    @Bean(SHIELD_MESSAGE_QUEUE_NAME)
    public Queue shieldMessageQueue() {
        return QueueBuilder.durable(SHIELD_MESSAGE_QUEUE_NAME).lazy().build();
    }

    // 2.2 重试队列
    @Bean(RETRY_MESSAGE_QUEUE_NAME)
    public Queue rertyMessageQueue() {
        return QueueBuilder.durable(RETRY_MESSAGE_QUEUE_NAME).lazy().build();
    }

    // 这里用于绑定==================================
    // 绑定1 1.1 1.2
    @Bean
    public Binding delayMessageQueueBinding(@Qualifier(DELAY_EXCHANGE_NAME) DirectExchange delayExchange, @Qualifier(DELAY_MESSAGE_QUEUE_NAME) Queue delayMessageQueue) {
        return BindingBuilder.bind(delayMessageQueue).to(delayExchange).with(DELAY_MESSAGE_BINDING_KEY);
    }

    @Bean
    public Binding shieldMessageQueueBinding(@Qualifier(DELAY_EXCHANGE_NAME) DirectExchange delayExchange, @Qualifier(SHIELD_MESSAGE_QUEUE_NAME) Queue shieldMessageQueue) {
        return BindingBuilder.bind(shieldMessageQueue).to(delayExchange).with(SHIELD_MESSAGE_BINDING_KEY);
    }

    // 绑定3 3.1
    @Bean
    public Binding rertyMessageQueueBinding(@Qualifier(RETRY_EXCHANGE_NAME) DirectExchange retryExchange, @Qualifier(RETRY_MESSAGE_QUEUE_NAME) Queue rertyMessageQueue) {
        return BindingBuilder.bind(rertyMessageQueue).to(retryExchange).with(RETRY_MESSAGE_BINDING_KEY);
    }

}
