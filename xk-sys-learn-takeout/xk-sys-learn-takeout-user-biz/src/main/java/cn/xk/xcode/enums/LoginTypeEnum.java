package cn.xk.xcode.enums;

import cn.xk.xcode.core.StringEnumValueToArray;
import cn.xk.xcode.utils.collections.ArrayUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public enum LoginTypeEnum implements StringEnumValueToArray {

    MOBILE("mobile"),

    PASSWORD("password");

    private final String type;

    @Override
    public String[] toArrayString() {
        return ArrayUtil.toArray(Arrays.stream(values()).map(LoginTypeEnum::getType).collect(Collectors.toList()));
    }

    public static LoginTypeEnum getLoginTypeEnum(String type) {
        return Arrays.stream(values()).filter(loginTypeEnum -> loginTypeEnum.getType().equals(type)).findFirst().orElse(null);
    }
}