package cn.xk.xcode.core.annotation;

/**
 * @Author xuk
 * @Date 2024/5/27 13:37
 * @Version 1.0
 * @Description StringEnumValueToArray
 */
public interface StringEnumValueToArray extends EnumValueToArray {

    @Override
    default int[] toIntArray() {
        return new int[0];
    }
}
