package cn.xk.xcode.core.aop;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.annotation.RateLimiter;
import cn.xk.xcode.core.redis.RedissonRateLimiter;
import cn.xk.xcode.core.resolver.RateLimiterKeyResolver;
import cn.xk.xcode.exception.GlobalErrorCodeConstants;
import cn.xk.xcode.exception.core.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/7/25 16:46
 * @Version 1.0
 * @Description RateLimiterAop
 */
@RequiredArgsConstructor
@Aspect
@Slf4j
public class RateLimiterAop
{
    private final Map<Class<? extends RateLimiterKeyResolver>, RateLimiterKeyResolver> keyResolvers;

    private final RedissonRateLimiter redissonRateLimiter;

    @Before("@annotation(rateLimiter)")
    public void beforePointCut(JoinPoint joinPoint, RateLimiter rateLimiter){
        Class<? extends RateLimiterKeyResolver> keyResolver = rateLimiter.keyResolver();
        RateLimiterKeyResolver rateLimiterKeyResolver = keyResolvers.get(keyResolver);
        Assert.isNull(rateLimiterKeyResolver, "找不到对应的 RateLimiterKeyResolver");
        String resKey = rateLimiterKeyResolver.resolver(joinPoint, rateLimiter);
        boolean res = redissonRateLimiter.tryAcquire(resKey, rateLimiter.limitCount(), rateLimiter.limitTime(), rateLimiter
                .timeUnit());
        if (!res) {
            log.info("[beforePointCut][方法({}) 参数({}) 请求过于频繁]", joinPoint.getSignature().toString(), joinPoint.getArgs());
            String message = StrUtil.blankToDefault(rateLimiter.message(),
                    GlobalErrorCodeConstants.TOO_MANY_REQUESTS.getMessage());
            throw new ServiceException(GlobalErrorCodeConstants.TOO_MANY_REQUESTS.getCode(), message);
        }
    }
}
