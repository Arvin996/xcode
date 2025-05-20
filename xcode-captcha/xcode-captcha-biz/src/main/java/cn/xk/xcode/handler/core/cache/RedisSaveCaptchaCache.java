package cn.xk.xcode.handler.core.cache;

import cn.xk.xcode.config.CaptchaProperties;
import cn.xk.xcode.handler.SaveCaptchaCacheStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Author xuk
 * @Date 2024/7/31 20:14
 * @Version 1.0
 * @Description RedisSaveCaptchaCache
 */
@RequiredArgsConstructor
public class RedisSaveCaptchaCache implements SaveCaptchaCacheStrategy {

    private final StringRedisTemplate stringRedisTemplate;

    private final CaptchaProperties captchaProperties;

    @Override
    public void save(String k, String v) {
        stringRedisTemplate.opsForValue().set(k, v, captchaProperties.getExpiredTime(), TimeUnit.SECONDS);
    }

    @Override
    public void remove(String k) {
        stringRedisTemplate.opsForValue().getAndDelete(k);
    }

    @Override
    public String get(String k) {
        return stringRedisTemplate.opsForValue().get(k);
    }
}
