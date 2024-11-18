package cn.xk.xcode.core.resolver.impl;

import cn.xk.xcode.annotation.RateLimiter;
import cn.xk.xcode.core.resolver.RateLimiterKeyResolver;
import cn.xk.xcode.utils.spring.SpringExpressionUtil;
import org.aspectj.lang.JoinPoint;

/**
 * @Author xuk
 * @Date 2024/7/25 16:20
 * @Version 1.0
 * @Description ExpressionRateLimiterKeyResolver
 */
public class ExpressionRateLimiterKeyResolver implements RateLimiterKeyResolver {
    @Override
    public String resolver(JoinPoint joinPoint, RateLimiter rateLimiter) {
        return (String) SpringExpressionUtil.parseExpression(joinPoint, rateLimiter.limitKey());
    }
}
