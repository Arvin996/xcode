package cn.xk.xcode.enums;

import lombok.Getter;

/**
 * @Author xuk
 * @Date 2024/6/24 12:49
 * @Version 1.0
 * @Description GlobalStatusEnum
 */
@Getter
public enum GlobalStatusEnum
{

    ENABLE("0"),
    DISABLE("1");

    private final String value;

    GlobalStatusEnum(String value) {
        this.value = value;
    }

}
