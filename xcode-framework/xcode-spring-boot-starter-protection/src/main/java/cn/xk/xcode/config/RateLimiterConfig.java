package cn.xk.xcode.config;

import cn.xk.xcode.core.aop.RateLimiterAop;
import cn.xk.xcode.core.redis.RedissonRateLimiter;
import cn.xk.xcode.core.resolver.RateLimiterKeyResolver;
import cn.xk.xcode.core.resolver.impl.*;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2024/8/27 20:56
 * @Version V1.0.0
 * @Description RateLimiterConfig
 */
@Configuration
public class RateLimiterConfig {

    @Value("${spring,application,name}")
    private String applicationName;

    @Bean
    public ClientIpRateLimiterKeyResolver clientIpRateLimiterKeyResolver(){
        return new ClientIpRateLimiterKeyResolver();
    }

    @Bean
    public DefaultRateLimiterKeyResolver defaultRateLimiterKeyResolver(){
        return new DefaultRateLimiterKeyResolver(applicationName);
    }

    @Bean
    public ExpressionRateLimiterKeyResolver expressionRateLimiterKeyResolver(){
        return new ExpressionRateLimiterKeyResolver();
    }

    @Bean
    public ServerNodeRateLimiterKeyResolver serverNodeRateLimiterKeyResolver(){
        return new ServerNodeRateLimiterKeyResolver();
    }

    @Bean
    public UserRateLimiterKeyResolver userRateLimiterKeyResolver(){
        return new UserRateLimiterKeyResolver();
    }

    @Bean
    public RedissonRateLimiter redissonRateLimiter(RedissonClient redissonClient){
        return new RedissonRateLimiter(redissonClient);
    }

    @Bean
    public RateLimiterAop rateLimiterAop(RedissonRateLimiter redissonRateLimiter, List<RateLimiterKeyResolver> keyResolvers){
        return new RateLimiterAop(redissonRateLimiter, keyResolvers);
    }
}
