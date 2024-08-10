package cn.xk.xcode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author xuk
 * @Date 2024/8/3 22:17
 * @Version 1.0
 * @Description sex
 */
@Getter
@AllArgsConstructor
public enum sex {
    MAN(0, "男"),
    FEMALE(1, "女")
    ;
    private final int sex;

    private final String name;

    public static String getSexName(int sex){
        return Arrays.stream(values()).filter(v -> v.getSex() == sex).map(cn.xk.xcode.enums.sex::getName).findFirst().orElse(null);

    }
}
