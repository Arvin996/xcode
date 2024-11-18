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

    public static void AssertTrueCastServiceEx(boolean flag, ErrorCode errorCode, Object... args) {
        if (flag) {
            ExceptionUtil.castServiceException(errorCode, args);
        }
    }

    public static void AssertFalseCastServiceEx(boolean flag, ErrorCode errorCode, Object... args) {
        AssertTrueCastServiceEx(!flag, errorCode, args);
    }

    public static void AssertTrueCastServerEx(boolean flag, ErrorCode errorCode, Object... args) {
        if (flag) {
            ExceptionUtil.castServerException(errorCode, args);
        }
    }

    public static void AssertFalseCastServerEx(boolean flag, ErrorCode errorCode, Object... args) {
        AssertTrueCastServerEx(!flag, errorCode, args);
    }

    public static void AssertTrueCastServiceEx(boolean flag, Supplier<ServerException> supplier, Object... args) {
        if (flag) {
            ServerException serverException = supplier.get();
            ExceptionUtil.castServiceException0(serverException.getCode(), serverException.getMsg(), args);
        }
    }

    public static void AssertFalseCastServiceEx(boolean flag, Supplier<ServerException> supplier, Object... args) {
        AssertTrueCastServiceEx(!flag, supplier, args);
    }

    public static void AssertTrueCastServerEx(boolean flag, Supplier<ServerException> supplier, Object... args) {
        if (flag) {
            ServerException serverException = supplier.get();
            ExceptionUtil.castServerException0(serverException.getCode(), serverException.getMsg(), args);
        }
    }

    public static void AssertFalseCastServerEx(boolean flag, Supplier<ServerException> supplier, Object... args) {
        AssertTrueCastServerEx(!flag, supplier, args);
    }


    public static void AssertNullCastServiceEx(Object obj, ErrorCode errorCode, Object... args) {
        AssertTrueCastServiceEx(Objects.isNull(obj), errorCode, args);
    }

    public static void AssertNullCastServerEx(Object obj, ErrorCode errorCode, Object... args) {
        AssertTrueCastServerEx(Objects.isNull(obj), errorCode, args);
    }

    public static void AssertNonNullCastServiceEx(Object obj, ErrorCode errorCode, Object... args) {
        AssertTrueCastServiceEx(Objects.nonNull(obj), errorCode, args);
    }

    public static void AssertNonNullCastServerEx(Object obj, ErrorCode errorCode, Object... args) {
        AssertTrueCastServerEx(Objects.nonNull(obj), errorCode, args);
    }

    public static <K, V> void AssertEmptyCastServiceEx(Map<K, V> map, ErrorCode errorCode, Object... args) {
        AssertTrueCastServiceEx(MapUtil.isEmpty(map), errorCode, args);
    }

    public static <K, V> void AssertEmptyCastServerEx(Map<K, V> map, ErrorCode errorCode, Object... args) {
        AssertTrueCastServerEx(MapUtil.isEmpty(map), errorCode, args);
    }

    public static <K, V> void AssertNonEmptyCastServiceEx(Map<K, V> map, ErrorCode errorCode, Object... args) {
        AssertTrueCastServiceEx(MapUtil.isNotEmpty(map), errorCode, args);
    }

    public static <K, V> void AssertNonEmptyCastServerEx(Map<K, V> map, ErrorCode errorCode, Object... args) {
        AssertTrueCastServerEx(MapUtil.isNotEmpty(map), errorCode, args);
    }

    public static <K> void AssertEmptyCastServiceEx(Collection<K> collection, ErrorCode errorCode, Object... args) {
        AssertTrueCastServiceEx(CollectionUtil.isEmpty(collection), errorCode, args);
    }

    public static <K> void AssertEmptyCastServerEx(Collection<K> collection, ErrorCode errorCode, Object... args) {
        AssertTrueCastServerEx(CollectionUtil.isEmpty(collection), errorCode, args);
    }

    public static <K> void AssertNonEmptyCastServiceEx(Collection<K> collection, ErrorCode errorCode, Object... args) {
        AssertTrueCastServiceEx(CollectionUtil.isNotEmpty(collection), errorCode, args);
    }

    public static <K> void AssertNonEmptyCastServerEx(Collection<K> collection, ErrorCode errorCode, Object... args) {
        AssertTrueCastServerEx(CollectionUtil.isNotEmpty(collection), errorCode, args);
    }

}
