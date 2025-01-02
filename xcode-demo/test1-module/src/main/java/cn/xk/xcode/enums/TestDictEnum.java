package cn.xk.xcode.enums;

import cn.xk.xcode.annotation.DictType;
import cn.xk.xcode.entity.IEnumDict;
import lombok.AllArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/12/26 15:45
 * @Version 1.0.0
 * @Description TestDictEnum
 **/
@DictType(dictType = "enum", dictName = "enum", dictDesc = "enum", dictCode = "enum")
@AllArgsConstructor
public enum TestDictEnum implements IEnumDict {
    dd("ddd", "ddd", "ddd"),
    cc("ccc", "ccc", "ccc"),
    ee("eee", "eee", "eee")

    ;

    private final String code;
    private final String name;
    private final String desc;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
