package cn.xk.xcode.enums;

import cn.xk.xcode.annotation.DictType;
import cn.xk.xcode.core.StringEnumValueToArray;
import cn.xk.xcode.entity.IEnumDict;
import cn.xk.xcode.utils.collections.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/7/31 19:48
 * @Version 1.0
 * @Description CheckCodeGenerateTypeEnum
 */
@Getter
@AllArgsConstructor
@DictType(dictName = "验证码生成类型枚举", dictType = "CheckCodeGenerateType")
public enum CheckCodeGenerateType implements IEnumDict, StringEnumValueToArray {
    EMAIL("email", "邮件验证码生成"),
    MOBILE("mobile", "手机验证码生成"),
    PIC("pic", "图片验证码生成");
    private final String code;

    private final String name;

    @Override
    public String[] toArrayString() {
        return ArrayUtil.toArray(Arrays.stream(values()).map(CheckCodeGenerateType::getCode).collect(Collectors.toList()));
    }
}
