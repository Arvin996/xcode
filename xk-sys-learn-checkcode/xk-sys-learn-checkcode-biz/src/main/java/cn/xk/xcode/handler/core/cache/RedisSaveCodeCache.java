package cn.xk.xcode.handler.core.cache;

import cn.xk.xcode.handler.SaveCheckCodeCacheStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2024/7/31 20:14
 * @Version 1.0
 * @Description RedisSaveCodeMemory
 */
@RequiredArgsConstructor
public class RedisSaveCodeCache implements SaveCheckCodeCacheStrategy
{
    private final StringRedisTemplate stringRedisTemplate;

    private final long expireTime;

    @Override
    public void save(String code) {
        stringRedisTemplate.opsForValue().set(code, code, expireTime, TimeUnit.SECONDS);
    }

    @Override
    public void remove(String code) {
        stringRedisTemplate.opsForValue().getAndDelete(code);
    }

    @Override
    public String get(String code) {
        return stringRedisTemplate.opsForValue().get(code);
    }
}
