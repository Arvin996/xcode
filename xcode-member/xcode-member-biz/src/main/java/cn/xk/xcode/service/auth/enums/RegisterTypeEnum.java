package cn.xk.xcode.service.auth.enums;

import cn.xk.xcode.core.StringEnumValueToArray;
import cn.xk.xcode.utils.collections.ArrayUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author Administrator
 * @Date 2024/8/29 15:03
 * @Version V1.0.0
 * @Description RegisterTypeEnum
 */
@RequiredArgsConstructor
@Getter
public enum RegisterTypeEnum implements StringEnumValueToArray {

    MOBILE("mobile"),

    EMAIL("email");

    private final String type;

    @Override
    public String[] toArrayString() {
        return ArrayUtil.toArray(Arrays.stream(values()).map(RegisterTypeEnum::getType).collect(Collectors.toList()));
    }
}
