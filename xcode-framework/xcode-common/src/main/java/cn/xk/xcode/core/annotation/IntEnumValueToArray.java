package cn.xk.xcode.core.annotation;

/**
 * @Author xuk
 * @Date 2024/5/27 13:36
 * @Version 1.0
 * @Description IntEnumValueToArray
 */
public interface IntEnumValueToArray extends EnumValueToArray {

    @Override
    default String[] toArrayString() {
        return new String[0];
    }
}
