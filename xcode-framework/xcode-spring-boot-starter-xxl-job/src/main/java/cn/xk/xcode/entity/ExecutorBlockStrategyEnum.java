package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2025/2/6 10:30
 * @Version 1.0.0
 * @Description ExecutorBlockStrategyEnum
 **/
@AllArgsConstructor
@Getter
public enum ExecutorBlockStrategyEnum {

    SERIAL_EXECUTION("SERIAL_EXECUTION", "单机串行"),
    DISCARD_LATER("DISCARD_LATER", "丢弃后期调度"),
    COVER_EARLY("COVER_EARLY", "覆盖之前调度");

    private final String value;
    private final String desc;
}
