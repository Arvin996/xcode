package cn.xk.xcode.annotation;

import cn.xk.xcode.core.resolver.IdempotentKeyResolver;
import cn.xk.xcode.core.resolver.impl.DefaultIdempotentKeyResolver;

import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2024/7/23 16:53
 * @Version 1.0
 * @Description idempotent
 */
public @interface Idempotent {
    int timeout() default 1;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    String message() default "重复请求，请稍后重试";

    Class<? extends IdempotentKeyResolver> keyResolver() default DefaultIdempotentKeyResolver.class;

    String IdeKey() default "";

    boolean unlockWhenException() default true;

    boolean unlockWhenSuccess() default true;
}
