package cn.xk.xcode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/8/3 22:17
 * @Version 1.0
 * @Description sex
 */
@Getter
@AllArgsConstructor
public enum Sex {
    MAN("0", "男"),
    FEMALE("1", "女")
    ;
    private final String sex;

    private final String name;

    public static String getSexName(String sex){
        return Arrays.stream(values()).filter(v -> Objects.equals(v.getSex(), sex)).map(cn.xk.xcode.enums.Sex::getName).findFirst().orElse(null);

    }
}
