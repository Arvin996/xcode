package cn.xk.xcode.enums;

import cn.hutool.core.util.ArrayUtil;
import cn.xk.xcode.core.annotation.StringEnumValueToArray;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/11/25 10:22
 * @Version 1.0.0
 * @Description GenerateTypeEnum
 **/
@AllArgsConstructor
@Getter
public enum GenerateTypeEnum implements StringEnumValueToArray {

    ENTITY("entity"),
    MAPPER("mapper"),
    XML("xml"),
    SERVICE("service"),
    IMPL("impl")
    ;
    private final String type;

    @Override
    public String[] toArrayString() {
        // list转数组
        return ArrayUtil.toArray(Arrays.stream(values()).map(GenerateTypeEnum::getType).collect(Collectors.toList()), String.class);
    }
}
