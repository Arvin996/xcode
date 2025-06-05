package cn.xk.xcode.config;

import cn.xk.xcode.balance.core.FirstLoadBalancer;
import cn.xk.xcode.balance.core.ILoadBalancer;
import cn.xk.xcode.handler.MessageHandlerHolder;
import cn.xk.xcode.handler.message.IHandler;
import cn.xk.xcode.limit.BaseRateLimiter;
import cn.xk.xcode.limit.GlobalRequestRateLimiter;
import cn.xk.xcode.limit.RateLimiterHolder;
import com.github.houbb.sensitive.word.api.IWordAllow;
import com.github.houbb.sensitive.word.api.IWordDeny;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.sensitive.word.support.allow.WordAllows;
import com.github.houbb.sensitive.word.support.deny.WordDenys;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/5 17:11
 * @Version 1.0.0
 * @Description MessageThreadPoolConfiguration
 **/
@Configuration
@EnableConfigurationProperties({XcodeMessageProperties.class})
public class MessageConfiguration {

    @Bean
    public MessageHandlerHolder messageHandlerHolder(List<IHandler> handlers) {
        return new MessageHandlerHolder(handlers);
    }

    @Bean
    public BaseRateLimiter globalRequestRateLimiter(RedissonClient redissonClient, XcodeMessageProperties xcodeMessageProperties) {
        return new GlobalRequestRateLimiter(redissonClient, xcodeMessageProperties);
    }

    @Bean
    public BaseRateLimiter channelSendMsgRateLimiter(RedissonClient redissonClient, XcodeMessageProperties xcodeMessageProperties) {
        return new GlobalRequestRateLimiter(redissonClient, xcodeMessageProperties);
    }

    @Bean
    public RateLimiterHolder rateLimiterHolder(List<BaseRateLimiter> rateLimiters) {
        return new RateLimiterHolder(rateLimiters);
    }

    @Bean
    @ConditionalOnMissingBean
    public ILoadBalancer iLoadBalancer() {
        return new FirstLoadBalancer();
    }

    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        // 设置序列化器（默认就是字符串序列化器，此处可省略）
        // template.setKeySerializer(RedisSerializer.string());
        // template.setValueSerializer(RedisSerializer.string());
        // template.setHashKeySerializer(RedisSerializer.string());
        // template.setHashValueSerializer(RedisSerializer.string());
        return new StringRedisTemplate(factory);
    }


}
