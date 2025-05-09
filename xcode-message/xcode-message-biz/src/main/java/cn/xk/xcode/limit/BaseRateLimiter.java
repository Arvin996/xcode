package cn.xk.xcode.limit;

import cn.xk.xcode.entity.task.MessageTask;
import cn.xk.xcode.enums.RateLimiterEnum;

/**
 * @Author xuk
 * @Date 2025/3/12 13:40
 * @Version 1.0.0
 * @Description BaseRateLimiter
 **/
public interface BaseRateLimiter {

    String RATE_LIMITER_KEY_PREFIX = "rate_limiter";

    Boolean rateLimit(MessageTask messageTask);

    RateLimiterEnum type();
}
