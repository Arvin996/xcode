package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2025/2/6 10:27
 * @Version 1.0.0
 * @Description MisfireStrategyEnum
 **/
@AllArgsConstructor
@Getter
public enum MisfireStrategyEnum {

    DO_NOTHING("DO_NOTHING", "忽略"),
    FIRE_ONCE_NOW("FIRE_ONCE_NOW", "立即执行一次");

    private final String value;
    private final String desc;
}
