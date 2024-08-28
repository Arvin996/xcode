package cn.xk.xcode.core.aop;

import cn.xk.xcode.annotation.Idempotent;
import cn.xk.xcode.core.redis.RedissonIdempotent;
import cn.xk.xcode.core.resolver.IdempotentKeyResolver;
import cn.xk.xcode.exception.GlobalErrorCodeConstants;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/7/25 17:12
 * @Version 1.0
 * @Description IdempotentAop
 */
@Aspect
@Slf4j
public class IdempotentAop {

    private final Map<Class<? extends IdempotentKeyResolver>, IdempotentKeyResolver> keyResolvers;
    private final RedissonIdempotent redissonIdempotent;

    public IdempotentAop(RedissonIdempotent redissonIdempotent, List<IdempotentKeyResolver> keyResolvers) {
        this.redissonIdempotent = redissonIdempotent;
        this.keyResolvers = CollectionUtil.convertMap(keyResolvers, IdempotentKeyResolver::getClass);
    }

    @Around(value = "@annotation(idempotent)")
    public Object aroundPointCut(ProceedingJoinPoint joinPoint, Idempotent idempotent) throws Throwable {
        IdempotentKeyResolver idempotentKeyResolver = keyResolvers.get(idempotent.keyResolver());
        Assert.notNull(idempotentKeyResolver, "找不到对应的 IdempotentKeyResolver");
        String resKey = idempotentKeyResolver.resolver(joinPoint, idempotent);
        boolean success = redissonIdempotent.tryAcquire(resKey, idempotent.timeout(), idempotent.timeUnit());
        // 锁定失败，抛出异常
        if (!success) {
            log.info("[aroundPointCut][方法({}) 参数({}) 存在重复请求]", joinPoint.getSignature().toString(), joinPoint.getArgs());
            throw new ServiceException(GlobalErrorCodeConstants.REPEATED_REQUESTS.getCode(), idempotent.message());
        }
        Object object;
        try {
            object = joinPoint.proceed();
        } catch (Throwable e) {
            if (idempotent.unlockWhenException()) {
                redissonIdempotent.unlock(resKey);
            }
            throw e;
        }
        if (idempotent.unlockWhenSuccess()) {
            redissonIdempotent.unlock(resKey);
        }
        return object;
    }


}
