package cn.xk.xcode.utils.object;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.core.ExceptionUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Author xuk
 * @Date 2024/5/28 13:32
 * @Version 1.0
 * @Description ObjectUtil
 */
public class ObjectUtil {
    /**
     * 复制对象，并忽略 Id 编号
     *
     * @param object   被复制对象
     * @param consumer 消费者，可以二次编辑被复制对象
     * @return 复制后的对象
     */
    public static <T> T cloneIgnoreId(T object, Consumer<T> consumer) {
        T result = cn.hutool.core.util.ObjectUtil.clone(object);
        Field field = ReflectUtil.getField(object.getClass(), "id");
        if (field != null) {
            ReflectUtil.setFieldValue(result, field, null);
        }
        // 二次编辑
        if (result != null) {
            consumer.accept(result);
        }
        return result;
    }

    public static <T> T cloneIgnoreId(T object) {
        return cloneIgnoreId(object, null);
    }

    @SafeVarargs
    public static <T> boolean equalsAny(T obj, T... array) {
        return Arrays.asList(array).contains(obj);
    }

    public static <T> void ifNullCastServiceException(T object, ErrorCode errorCode, Object... args) {
        if (Objects.isNull(object)) {
            if (ArrayUtil.isEmpty(args)) {
                ExceptionUtil.castServiceException(errorCode);
            } else {
                ExceptionUtil.castServiceException(errorCode, args);
            }
        }
    }

    public static <T> void ifNullCastServerException(T object, ErrorCode errorCode, Object... args) {
        if (Objects.isNull(object)) {
            if (ArrayUtil.isEmpty(args)) {
                ExceptionUtil.castServerException(errorCode);
            } else {
                ExceptionUtil.castServerException(errorCode, args);
            }
        }
    }

    @SuppressWarnings("all")
    public static <U, T> T computeIfNotNull(U object, ErrorCode errorCode, Function<U, T> func, Object... args) {
        if (Objects.isNull(object)) {
            if (ArrayUtil.isEmpty(args)) {
                ExceptionUtil.castServiceException(errorCode);
            } else {
                ExceptionUtil.castServiceException(errorCode, args);
            }
        }
        if (Objects.isNull(func)) {
            return (T) object;
        }
        return func.apply(object);
    }

    public static <U> U computeIfNotNull(U object, ErrorCode errorCode, Object... args) {
        if (Objects.isNull(object)) {
            if (ArrayUtil.isEmpty(args)) {
                ExceptionUtil.castServiceException(errorCode);
            } else {
                ExceptionUtil.castServiceException(errorCode, args);
            }
        }
        return computeIfNotNull(object, errorCode, Function.identity(), args);
    }

    public static <T> T returnIfNotNullCastServiceEx(T object, ErrorCode errorCode, Object... args) {
        if (Objects.isNull(object)) {
            if (ArrayUtil.isEmpty(args)) {
                ExceptionUtil.castServiceException(errorCode);
            } else {
                ExceptionUtil.castServiceException(errorCode, args);
            }
        }
        return object;
    }

    public static <T> T returnIfNotNullCastServerEx(T object, ErrorCode errorCode, Object... args) {
        if (Objects.isNull(object)) {
            if (ArrayUtil.isEmpty(args)) {
                ExceptionUtil.castServerException(errorCode);
            } else {
                ExceptionUtil.castServerException(errorCode, args);
            }
        }
        return object;
    }

    public static boolean isNull(Object object) {
        return Objects.isNull(object);
    }

    public static boolean isNotNull(Object object) {
        return Objects.nonNull(object);
    }

}
