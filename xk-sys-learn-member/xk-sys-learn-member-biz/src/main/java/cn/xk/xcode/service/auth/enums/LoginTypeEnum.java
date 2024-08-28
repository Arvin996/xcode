package cn.xk.xcode.service.auth.enums;

import cn.xk.xcode.core.StringEnumValueToArray;
import cn.xk.xcode.utils.collections.ArrayUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author Administrator
 * @Date 2024/8/27 17:23
 * @Version V1.0.0
 * @Description LoginTypeEnum
 */
@RequiredArgsConstructor
@Getter
public enum LoginTypeEnum implements StringEnumValueToArray {

    PHONE("phone"),

    EMAIL("email"),

    PASSWORD("password");

    private final String type;

    @Override
    public String[] toArrayString() {
        return ArrayUtil.toArray(Arrays.stream(values()).map(LoginTypeEnum::getType).collect(Collectors.toList()));
    }

    public static LoginTypeEnum getLoginTypeEnum(String type){
        return Arrays.stream(values()).filter(loginTypeEnum -> loginTypeEnum.getType().equals(type)).findFirst().orElse(null);
    }
}
