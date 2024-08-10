package cn.xk.xcode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2024/8/3 22:31
 * @Version 1.0
 * @Description GlobalStatusEnum
 */
@Getter
@AllArgsConstructor
public enum GlobalStatusEnum {
    ENABLE(0, "启用"),
    DISABLE(1, "停用");
    private final int code;

    private final String name;
}
