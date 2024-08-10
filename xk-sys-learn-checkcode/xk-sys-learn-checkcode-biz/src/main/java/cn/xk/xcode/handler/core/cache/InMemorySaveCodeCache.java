package cn.xk.xcode.handler.core.cache;

import cn.xk.xcode.handler.SaveCheckCodeCacheStrategy;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/7/31 20:15
 * @Version 1.0
 * @Description InMemorySaveCodeStrategy
 */
@RequiredArgsConstructor
public class InMemorySaveCodeCache implements SaveCheckCodeCacheStrategy {

   private final Cache<String, String> cache;

    @Override
    public void save(String code) {
        cache.put(code, code);
    }

    @Override
    public void remove(String code) {
        cache.invalidate(code);
    }

    @Override
    public String get(String code) {
        return cache.getIfPresent(code);
    }
}
