package cn.xk.xcode.core.resolver;

import cn.xk.xcode.annotation.RateLimiter;
import org.aspectj.lang.JoinPoint;

/**
 * @Author xuk
 * @Date 2024/7/23 16:59
 * @Version 1.0
 * @Description IdempotentKeyResolver
 */
public interface IdempotentKeyResolver extends BaseKeyResolver
{
    @Override
    default String resolver(JoinPoint joinPoint, RateLimiter rateLimiter){
        return null;
    }
}
