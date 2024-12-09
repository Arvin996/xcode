package cn.xk.xcode.handler.core.cache;

import cn.xk.xcode.handler.SaveCaptchaCacheStrategy;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/7/31 20:15
 * @Version 1.0
 * @Description InMemorySaveCaptchaCache
 */
@RequiredArgsConstructor
public class InMemorySaveCaptchaCache implements SaveCaptchaCacheStrategy {

   private final Cache<String, String> cache;

    @Override
    public void save(String k, String v) {
        cache.put(k, v);
    }

    @Override
    public void remove(String k) {
        cache.invalidate(k);
    }

    @Override
    public String get(String k) {
        return cache.getIfPresent(k);
    }
}
