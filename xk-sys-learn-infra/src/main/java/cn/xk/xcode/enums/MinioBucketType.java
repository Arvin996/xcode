package cn.xk.xcode.enums;

import lombok.Getter;

/**
 * @Author xuk
 * @Date 2024/6/25 14:18
 * @Version 1.0
 * @Description MinioBucketType
 */
@Getter
public enum MinioBucketType
{
    VIDEO("video"),

    IMAGE("image"),

    FILE("file");

    private final String type;

    MinioBucketType(String type)
    {
        this.type = type;
    }

}
