package cn.xk.xcode.exception.core;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.utils.collections.CollectionUtil;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @Author xuk
 * @Date 2024/11/18 10:49
 * @Version 1.0.0
 * @Description AssertUtil 断言工具类
 **/
public class AssertUtil {

    private AssertUtil() {
    }

    public static void assertTrueCastServiceEx(boolean flag, ErrorCode errorCode, Object... args) {
        if (flag) {
            ExceptionUtil.castServiceException(errorCode, args);
        }
    }

    public static void assertFalseCastServiceEx(boolean flag, ErrorCode errorCode, Object... args) {
        assertTrueCastServiceEx(!flag, errorCode, args);
    }

    public static void assertTrueCastServerEx(boolean flag, ErrorCode errorCode, Object... args) {
        if (flag) {
            ExceptionUtil.castServerException(errorCode, args);
        }
    }

    public static void assertFalseCastServerEx(boolean flag, ErrorCode errorCode, Object... args) {
        assertTrueCastServerEx(!flag, errorCode, args);
    }

    public static void assertTrueCastServiceEx(boolean flag, Supplier<ServerException> supplier, Object... args) {
        if (flag) {
            ServerException serverException = supplier.get();
            ExceptionUtil.castServiceException0(serverException.getCode(), serverException.getMsg(), args);
        }
    }

    public static void assertFalseCastServiceEx(boolean flag, Supplier<ServerException> supplier, Object... args) {
        assertTrueCastServiceEx(!flag, supplier, args);
    }

    public static void assertTrueCastServerEx(boolean flag, Supplier<ServerException> supplier, Object... args) {
        if (flag) {
            ServerException serverException = supplier.get();
            ExceptionUtil.castServerException0(serverException.getCode(), serverException.getMsg(), args);
        }
    }

    public static void assertFalseCastServerEx(boolean flag, Supplier<ServerException> supplier, Object... args) {
        assertTrueCastServerEx(!flag, supplier, args);
    }


    public static void assertNullCastServiceEx(Object obj, ErrorCode errorCode, Object... args) {
        assertTrueCastServiceEx(Objects.isNull(obj), errorCode, args);
    }

    public static void assertNullCastServerEx(Object obj, ErrorCode errorCode, Object... args) {
        assertTrueCastServerEx(Objects.isNull(obj), errorCode, args);
    }

    public static void assertNonNullCastServiceEx(Object obj, ErrorCode errorCode, Object... args) {
        assertTrueCastServiceEx(Objects.nonNull(obj), errorCode, args);
    }

    public static void assertNonNullCastServerEx(Object obj, ErrorCode errorCode, Object... args) {
        assertTrueCastServerEx(Objects.nonNull(obj), errorCode, args);
    }

    public static <K, V> void assertEmptyCastServiceEx(Map<K, V> map, ErrorCode errorCode, Object... args) {
        assertTrueCastServiceEx(MapUtil.isEmpty(map), errorCode, args);
    }

    public static <K, V> void assertEmptyCastServerEx(Map<K, V> map, ErrorCode errorCode, Object... args) {
        assertTrueCastServerEx(MapUtil.isEmpty(map), errorCode, args);
    }

    public static <K, V> void assertNonEmptyCastServiceEx(Map<K, V> map, ErrorCode errorCode, Object... args) {
        assertTrueCastServiceEx(MapUtil.isNotEmpty(map), errorCode, args);
    }

    public static <K, V> void assertNonEmptyCastServerEx(Map<K, V> map, ErrorCode errorCode, Object... args) {
        assertTrueCastServerEx(MapUtil.isNotEmpty(map), errorCode, args);
    }

    public static <K> void assertEmptyCastServiceEx(Collection<K> collection, ErrorCode errorCode, Object... args) {
        assertTrueCastServiceEx(CollectionUtil.isEmpty(collection), errorCode, args);
    }

    public static <K> void assertEmptyCastServerEx(Collection<K> collection, ErrorCode errorCode, Object... args) {
        assertTrueCastServerEx(CollectionUtil.isEmpty(collection), errorCode, args);
    }

    public static <K> void assertNonEmptyCastServiceEx(Collection<K> collection, ErrorCode errorCode, Object... args) {
        assertTrueCastServiceEx(CollectionUtil.isNotEmpty(collection), errorCode, args);
    }

    public static <K> void assertNonEmptyCastServerEx(Collection<K> collection, ErrorCode errorCode, Object... args) {
        assertTrueCastServerEx(CollectionUtil.isNotEmpty(collection), errorCode, args);
    }

}
