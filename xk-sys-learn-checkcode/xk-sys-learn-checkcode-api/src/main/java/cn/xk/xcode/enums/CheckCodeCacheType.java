package cn.xk.xcode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author xuk
 * @Date 2024/7/31 20:29
 * @Version 1.0
 * @Description CheckCodeCacheType
 */
@AllArgsConstructor
@Getter
public enum CheckCodeCacheType
{
    REDIS("redis"),
    LOCAL("local");
    private final String type;

    public static boolean isValid(String type){
        return Arrays.stream(values()).anyMatch(o -> o.getType().equals(type));
    }
}
