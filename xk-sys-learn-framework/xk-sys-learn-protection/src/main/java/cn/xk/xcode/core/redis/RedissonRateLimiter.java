package cn.xk.xcode.core.redis;

import lombok.RequiredArgsConstructor;
import org.redisson.api.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2024/7/25 16:34
 * @Version 1.0
 * @Description RedissonRateLimiter
 */
@RequiredArgsConstructor
public class RedissonRateLimiter {
    private static final String RATE_LIMITER = "rate_limiter:%s";

    private final RedissonClient redissonClient;

    public Boolean tryAcquire(String key, long count, int time, TimeUnit timeUnit) {
        RRateLimiter rRateLimiter = getRRateLimiter(key, count, time, timeUnit);
        return rRateLimiter.tryAcquire();
    }

    private static String formatKey(String key) {
        return String.format(RATE_LIMITER, key);
    }

    private RRateLimiter getRRateLimiter(String key, long count, int time, TimeUnit timeUnit) {
        String redisKey = formatKey(key);
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(redisKey);
        long seconds = timeUnit.toSeconds(time);
        RateLimiterConfig config = rateLimiter.getConfig();
        if (Objects.isNull(config)) {
            rateLimiter.trySetRate(RateType.OVERALL, count, seconds, RateIntervalUnit.SECONDS);
            return rateLimiter;
        }
        // 2. 如果存在，并且配置相同，则直接返回
        if (config.getRateType() == RateType.OVERALL
                && Objects.equals(config.getRate(), count)
                && Objects.equals(config.getRateInterval(), TimeUnit.SECONDS.toMillis(seconds))) {
            return rateLimiter;
        }
        // 更新
        rateLimiter.setRate(RateType.OVERALL, count, seconds, RateIntervalUnit.SECONDS);
        return rateLimiter;
    }
}
