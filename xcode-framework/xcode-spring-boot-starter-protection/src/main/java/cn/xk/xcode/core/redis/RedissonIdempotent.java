package cn.xk.xcode.core.redis;

import cn.xk.xcode.core.lock.DistributedLock;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2024/7/25 16:53
 * @Version 1.0
 * @Description RedisIdempotent
 */
@RequiredArgsConstructor
public class RedissonIdempotent {
    private static final String IDEMPOTENT = "idempotent:%s";

    private final DistributedLock distributedLock;

    public boolean tryAcquire(String key, long timeout, TimeUnit timeUnit){
        String formatKey = formatKey(key);
        return distributedLock.tryLock(formatKey, timeout, timeUnit);
    }

    public void unlock(String key) {
        String redisKey = formatKey(key);
        distributedLock.unlock(redisKey);

    }

    private static String formatKey(String key) {
        return String.format(IDEMPOTENT, key);
    }
}
