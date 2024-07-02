package cn.xk.xcode.config;

import cn.xk.xcode.core.RedisMqTemplate;
import cn.xk.xcode.listener.AbstractRedisMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/7/2 10:13
 * @Version 1.0
 * @Description RedisMqConsumerConfig
 */
@Configuration
@Slf4j
public class RedisMqConsumerConfig
{
    /**
     * 创建 Redis Pub/Sub 广播消费的容器
     */
    @Bean
    @ConditionalOnBean(AbstractRedisMessageListener.class) // 只有 AbstractChannelMessageListener 存在的时候，才需要注册 Redis pubsub 监听
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisMqTemplate redisMQTemplate, List<AbstractRedisMessageListener> listeners) {
        // 创建 RedisMessageListenerContainer 对象
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // 设置 RedisConnection 工厂。
        container.setConnectionFactory(redisMQTemplate.getRedisTemplate().getRequiredConnectionFactory());
        // 添加监听器
        listeners.forEach(listener -> {
            container.addMessageListener(listener, new ChannelTopic(listener.channel()));
            log.info("[redisMessageListenerContainer][注册 Channel({}) 对应的监听器({})]",
                    listener.channel(), listener.getClass().getName());
        });
        return container;
    }

}
