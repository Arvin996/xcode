package cn.xk.xcode.core.resolver;

import cn.xk.xcode.annotation.Idempotent;
import org.aspectj.lang.JoinPoint;

/**
 * @Author xuk
 * @Date 2024/7/23 16:58
 * @Version 1.0
 * @Description RateLimiterKeyResolver
 */
public interface RateLimiterKeyResolver extends BaseKeyResolver {
    @Override
    default String resolver(JoinPoint joinPoint, Idempotent idempotent) {
        return null;
    }
}
