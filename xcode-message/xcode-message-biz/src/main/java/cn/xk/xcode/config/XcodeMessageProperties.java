package cn.xk.xcode.config;

import cn.xk.xcode.enums.RateLimiterEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author xuk
 * @Date 2025/3/12 13:25
 * @Version 1.0.0
 * @Description XcodeMessageProperties
 **/
@ConfigurationProperties("xcode.message")
@Data
public class XcodeMessageProperties {
    private limiter limiter;
    private Integer failRetryTimes = 3;

    @Data
    public static class limiter {
        private RateLimiterEnum strategy = RateLimiterEnum.TOTAL_REQUEST_LIMITER;
        private Long channelNumRatePerSecond = 100L;
        private Long totalRequestRatePerSecond = 150L;
    }
}
