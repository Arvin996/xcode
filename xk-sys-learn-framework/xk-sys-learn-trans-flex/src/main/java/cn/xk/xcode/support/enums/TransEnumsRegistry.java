package cn.xk.xcode.support.enums;

import cn.hutool.core.util.ClassUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 9:01
 * @description
 */
@Getter
@RequiredArgsConstructor
@Slf4j
public class TransEnumsRegistry {
    public final List<BaseEnum> enums;

    public TransEnumsRegistry register(Class<? extends BaseEnum> aClass) {
        this.addEnumsEntity(aClass);
        return this;
    }

    public void addEnumsEntity(Class<? extends BaseEnum> aClass) {
        if (ClassUtil.isEnum(aClass)) {
            BaseEnum[] enumConstants = aClass.getEnumConstants();
            enums.addAll(Arrays.asList(enumConstants));
        } else {
            log.warn("{} is not enum", aClass.getName());
        }
    }
}
