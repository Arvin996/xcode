package cn.xk.xcode.enums;

import cn.xk.xcode.core.annotation.StringEnumValueToArray;
import cn.xk.xcode.utils.collections.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/6/25 14:18
 * @Version 1.0
 * @Description MinioBucketType
 */
@Getter
public enum MinioBucketType implements StringEnumValueToArray
{
    VIDEO("video"),

    IMAGE("image"),

    FILE("file");

    private final String type;

    MinioBucketType(String type)
    {
        this.type = type;
    }

    public static MinioBucketType getByType(String type){
        return Arrays.stream(values()).filter(v -> v.getType().equals(type)).findFirst().orElse(null);
    }

    @Override
    public String[] toArrayString() {
        return ArrayUtil.toArray(Arrays.stream(values()).map(MinioBucketType::getType).collect(Collectors.toList()));
    }
}
