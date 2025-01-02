package cn.xk.xcode.enums;

import cn.xk.xcode.support.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2024/12/30 9:10
 * @Version 1.0.0
 * @Description TestFlexTransEnum
 **/
@AllArgsConstructor
@Getter
public enum TestFlexTransEnum implements BaseEnum {

    AAA("aaa", "aaa"),
    BBB("bbb", "bbb"),
    CCC("ccc", "ccc");
    private final String key;
    private final String value;
    ;

    @Override
    public Object key() {
        return key;
    }

    @Override
    public Object value() {
        return value;
    }

    @Override
    public String enumType() {
        return "enum";
    }
}
