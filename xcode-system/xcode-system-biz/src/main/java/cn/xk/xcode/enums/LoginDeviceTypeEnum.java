package cn.xk.xcode.enums;

import cn.xk.xcode.core.annotation.StringEnumValueToArray;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author xuk
 * @Date 2024/6/24 13:16
 * @Version 1.0
 * @Description LoginDeviceEnum
 */
@Getter
public enum LoginDeviceTypeEnum implements StringEnumValueToArray{

    PC("PC"),

    MOBILE("MOBILE");

    private final String value;

    LoginDeviceTypeEnum(String value)
    {
        this.value = value;
    }

    public String[] toArrayString() {
        return Arrays.stream(values()).map(LoginDeviceTypeEnum::getValue).toArray(String[]::new);
    }
}
