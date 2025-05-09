package cn.xk.xcode.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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
    public static final String SEND_MESSAGE_BINDING_KEY = "sendMessageBindingKey";
    public static final String WITHDRAW_MESSAGE_BINDING_KEY = "withdrawMessageBindingKey";
    public static final String MESSAGE_HANDLE_FAIL_BINDING_KEY = "messageHandleFailBindingKey";

    // 声明队列名称
    public static final String DELAY_MESSAGE_QUEUE_NAME = "delayMessageQueue";
    public static final String SHIELD_MESSAGE_QUEUE_NAME = "shieldMessageQueue";
    public static final String SEND_MESSAGE_QUEUE_NAME = "sendMessageQueue";
    public static final String WITHDRAW_MESSAGE_QUEUE_NAME = "withdrawMessageQueue";
    public static final String MESSAGE_HANDLE_FAIL_QUEUE_NAME = "messageHandleFailQueue";
    // 声明交换机名称
    public static final String DELAY_EXCHANGE_NAME = "delayExchange";
    public static final String MESSAGE_HANDLER_EXCHANGE_NAME = "messageHandlerExchange";
    public static final String MESSAGE_HANDLE_FAIL_EXCHANGE_NAME = "messageHandleFailExchange";

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
//        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
//        });
        rabbitTemplate.setMessageConverter(messageConverter());
    }

    public MessageConverter messageConverter() {
        // 1.定义消息转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
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

    // 2. 发送消息以及撤销消息的direct交换机
    @Bean(MESSAGE_HANDLER_EXCHANGE_NAME)
    public DirectExchange messageHandlerExchange() {
        return ExchangeBuilder
                .directExchange(MESSAGE_HANDLER_EXCHANGE_NAME)
                .durable(true)  // 设置持久化
                .build();
    }

    // 3. 消息者消息消息失败 并且达到最大重试次数后 消息放到此交换机
    @Bean(MESSAGE_HANDLE_FAIL_EXCHANGE_NAME)
    public DirectExchange messageHandleFailExchange() {
        return ExchangeBuilder
                .directExchange(MESSAGE_HANDLE_FAIL_EXCHANGE_NAME)
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

    // 2.1 发送消息的队列
    @Bean(SEND_MESSAGE_QUEUE_NAME)
    public Queue sendMessageQueue() {
        return QueueBuilder.durable(SEND_MESSAGE_QUEUE_NAME).lazy().build();
    }

    // 2.2 撤销消息的队列
    @Bean(WITHDRAW_MESSAGE_QUEUE_NAME)
    public Queue withdrawMessageQueue() {
        return QueueBuilder.durable(WITHDRAW_MESSAGE_QUEUE_NAME).lazy().build();
    }

    // 3.1 消息处理失败的队列
    @Bean(MESSAGE_HANDLE_FAIL_QUEUE_NAME)
    public Queue messageHandleFailQueue() {
        return QueueBuilder.durable(MESSAGE_HANDLE_FAIL_QUEUE_NAME).lazy().build();
    }

    // 这里用于绑定==================================
    // 绑定1 1.1 1.2
    @Bean
    public Binding delayMessageQueueBinding(@Qualifier("delayExchange") DirectExchange delayExchange, @Qualifier("delayMessageQueue") Queue delayMessageQueue) {
        return BindingBuilder.bind(delayMessageQueue).to(delayExchange).with(DELAY_MESSAGE_BINDING_KEY);
    }

    @Bean
    public Binding shieldMessageQueueBinding(@Qualifier("delayExchange") DirectExchange delayExchange, @Qualifier("shieldMessageQueue") Queue shieldMessageQueue) {
        return BindingBuilder.bind(shieldMessageQueue).to(delayExchange).with(SHIELD_MESSAGE_BINDING_KEY);
    }

    // 绑定2 2.3 2.4
    @Bean
    public Binding sendMessageQueueBinding(@Qualifier("messageHandlerExchange") DirectExchange messageHandlerExchange, @Qualifier("sendMessageQueue") Queue sendMessageQueue) {
        return BindingBuilder.bind(sendMessageQueue).to(messageHandlerExchange).with(SEND_MESSAGE_BINDING_KEY);
    }

    @Bean
    public Binding withdrawMessageQueueBinding(@Qualifier("messageHandlerExchange") DirectExchange messageHandlerExchange, @Qualifier("withdrawMessageQueue") Queue withdrawMessageQueue) {
        return BindingBuilder.bind(withdrawMessageQueue).to(messageHandlerExchange).with(WITHDRAW_MESSAGE_BINDING_KEY);
    }

    // 绑定3 3.1
    @Bean
    public Binding messageHandleFailQueueBinding(@Qualifier("messageHandleFailExchange") DirectExchange messageHandleFailExchange, @Qualifier("messageHandleFailQueue") Queue messageHandleFailQueue) {
        return BindingBuilder.bind(messageHandleFailQueue).to(messageHandleFailExchange).with(MESSAGE_HANDLE_FAIL_BINDING_KEY);
    }

    @Bean
    public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate){
        return new RepublishMessageRecoverer(rabbitTemplate, MESSAGE_HANDLE_FAIL_EXCHANGE_NAME,  MESSAGE_HANDLE_FAIL_BINDING_KEY);
    }

}
