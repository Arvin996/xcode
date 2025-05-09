package cn.xk.xcode.limit;

import cn.xk.xcode.config.XcodeMessageProperties;
import cn.xk.xcode.enums.RateLimiterEnum;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/3/12 14:49
 * @Version 1.0.0
 * @Description RateLimiterHolder
 **/
@RequiredArgsConstructor
public class RateLimiterHolder {

    private final Map<RateLimiterEnum, BaseRateLimiter> rateLimiterMap = new HashMap<>();

    private final XcodeMessageProperties xcodeMessageProperties;

    public RateLimiterHolder(List<BaseRateLimiter> rateLimiters) {
        for (BaseRateLimiter rateLimiter : rateLimiters) {
            rateLimiterMap.put(rateLimiter.type(), rateLimiter);
        }
        this.xcodeMessageProperties = new XcodeMessageProperties();
    }

    public BaseRateLimiter getRateLimiter() {
        return rateLimiterMap.get(xcodeMessageProperties.getLimiter().getStrategy());
    }
}
