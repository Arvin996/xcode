package cn.xk.xcode.core.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2024/7/2 10:38
 * @Version 1.0
 * @Description RedissonLock
 */
@RequiredArgsConstructor
@Slf4j
public class DistributedLock {

    private final RedissonClient redissonClient;

    /**
     * 互斥锁，seconds秒后自动失效
     *
     * @param key     key
     * @param seconds 多少秒失效
     */
    public boolean tryLock(String key, long seconds) {
        return tryLock(key, seconds, TimeUnit.SECONDS);
    }


    public boolean tryLock(String key, long time, TimeUnit timeUnit) {
        RLock rLock = redissonClient.getLock(key);
        try {
            return rLock.tryLock(time, timeUnit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * @param key       锁key
     * @param waitTime  等待时间
     * @param leaseTime 锁时间
     * @param timeUnit  时间单位
     * @return 是否获取到锁
     */
    public boolean tryLock(String key, long waitTime, long leaseTime, TimeUnit timeUnit) {
        RLock rLock = redissonClient.getLock(key);
        try {
            return rLock.tryLock(waitTime, leaseTime, timeUnit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 互斥锁，自动续期
     *
     * @param key key
     */
    public boolean tryLock(String key) {
        RLock rLock = redissonClient.getLock(key);
        boolean locked = rLock.tryLock();
        log.info("tryLock: key={},locked={}", key, locked);
        return locked;
    }

    /**
     * 手动释放锁
     *
     * @param key key
     */
    public void unlock(String key) {
        RLock rLock = redissonClient.getLock(key);
        if (rLock.isLocked()) {
            rLock.unlock();
            log.info("unlock: key={}", key);
        }
    }

    /**
     * 联锁 加锁
     */
    public boolean multiLock(List<String> redisKeyList) {
        try {
            RLock multiLock = getMultiLock(redisKeyList);
            return multiLock.tryLock();
        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }
    }

    private RLock getMultiLock(List<String> redisKeyList) {
        RLock[] locks = new RLock[redisKeyList.size()];
        for (int i = 0; i < redisKeyList.size(); i++) {
            RLock lock = redissonClient.getLock(redisKeyList.get(i));
            locks[i] = lock;
        }
        return redissonClient.getMultiLock(locks);
    }

    /**
     * 联锁 解锁
     */
    public void unMultiLock(List<String> redisKeyList) {
        RLock multiLock = getMultiLock(redisKeyList);
        multiLock.unlock();
    }
}
