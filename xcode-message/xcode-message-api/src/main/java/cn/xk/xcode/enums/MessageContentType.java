package cn.xk.xcode.enums;

import cn.xk.xcode.core.annotation.StringEnumValueToArray;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author xuk
 * @Date 2025/3/10 16:03
 * @Version 1.0.0
 * @Description MessageContentType
 **/
@AllArgsConstructor
@Getter
public enum MessageContentType implements StringEnumValueToArray {

    PLAIN("plain", "文本"),
    TEMPLATE("template", "模板");

    private final String code;
    private final String desc;

    @Override
    public String[] toArrayString() {
        return Arrays.stream(values()).map(MessageContentType::getCode).toArray(String[]::new);
    }
}
