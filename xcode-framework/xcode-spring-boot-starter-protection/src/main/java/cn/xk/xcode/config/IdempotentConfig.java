package cn.xk.xcode.config;

import cn.xk.xcode.core.aop.IdempotentAop;
import cn.xk.xcode.core.lock.DistributedLock;
import cn.xk.xcode.core.redis.RedissonIdempotent;
import cn.xk.xcode.core.resolver.IdempotentKeyResolver;
import cn.xk.xcode.core.resolver.impl.DefaultIdempotentKeyResolver;
import cn.xk.xcode.core.resolver.impl.ExpressionIdempotentKeyResolver;
import cn.xk.xcode.core.resolver.impl.UserIdempotentKeyResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author Administrator
 * @Date 2024/8/27 20:56
 * @Version V1.0.0
 * @Description IdempotentConfig
 */
@Configuration
public class IdempotentConfig {

    @Value("${spring,application,name}")
    private String applicationName;

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver(){
        return new DefaultIdempotentKeyResolver(applicationName);
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver(){
        return new ExpressionIdempotentKeyResolver();
    }

    @Bean
    public UserIdempotentKeyResolver userIdempotentKeyResolver(){
        return new UserIdempotentKeyResolver();
    }

    @Bean
    public RedissonIdempotent redissonIdempotent(DistributedLock distributedLock){
        return new RedissonIdempotent(distributedLock);
    }

    @Bean
    public IdempotentAop idempotentAop(RedissonIdempotent redissonIdempotent, List<IdempotentKeyResolver> keyResolvers){
        return new IdempotentAop(redissonIdempotent, keyResolvers);
    }
}
