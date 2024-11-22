package cn.xk.xcode.core;

import cn.hutool.core.util.ObjUtil;
import cn.xk.xcode.core.annotation.StringEnumValueToArray;
import cn.xk.xcode.utils.collections.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum CommonStatusEnum implements StringEnumValueToArray {

    ENABLE("0", "开启"),
    DISABLE("1", "关闭");


    /**
     * 状态值
     */
    private final String status;
    /**
     * 状态名
     */
    private final String name;

    public static boolean isEnable(String status) {
        return ObjUtil.equal(ENABLE.status, status);
    }

    public static boolean isDisable(String status) {
        return ObjUtil.equal(DISABLE.status, status);
    }

    @Override
    public String[] toArrayString() {
        return ArrayUtil.toArray(Arrays.stream(values()).map(v -> String.valueOf(v.status)).collect(Collectors.toList()));
    }
}