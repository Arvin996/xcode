package cn.xk.xcode.service.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author xuk
 * @Date 2025/3/13 14:28
 * @Version 1.0.0
 * @Description SmsPlatEnum
 **/
@AllArgsConstructor
@Getter
public enum SmsPlatEnum {

    YUNPIAN("yunpian"),
    TENG_XUN_CLOUD("tencent_cloud");

    private final String name;

    public static SmsPlatEnum getByName(String name) {
        return Arrays.stream(SmsPlatEnum.values()).filter(item -> item.getName().equals(name)).findFirst().orElse(null);
    }
}
