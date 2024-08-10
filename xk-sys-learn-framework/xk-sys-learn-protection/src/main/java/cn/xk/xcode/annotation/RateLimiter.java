package cn.xk.xcode.annotation;

import cn.xk.xcode.core.resolver.RateLimiterKeyResolver;
import cn.xk.xcode.core.resolver.impl.DefaultRateLimiterKeyResolver;

import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2024/7/23 16:53
 * @Version 1.0
 * @Description RateLimiter
 */
public @interface RateLimiter
{
    String limitKey() default "";

    int limitTime() default 1;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    long limitCount() default 100;

    String message() default "访问过于频繁，请稍后再试";

    Class<? extends RateLimiterKeyResolver> keyResolver() default DefaultRateLimiterKeyResolver.class;

}
