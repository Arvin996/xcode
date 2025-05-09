package cn.xk.xcode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2025/3/12 11:27
 * @Version 1.0.0
 * @Description RateLimiterEnum
 **/
@AllArgsConstructor
@Getter
public enum RateLimiterEnum {

    CHANNEL_SEND_SUER_NUM_LIMITER,
    TOTAL_REQUEST_LIMITER
}
