package cn.xk.xcode.utils.collections;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.IterUtil;
import cn.hutool.core.util.ObjectUtil;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static cn.xk.xcode.utils.collections.CollectionUtil.createEmptyList;
import static cn.xk.xcode.utils.collections.CollectionUtil.createEmptySet;

/**
 * @Author xuk
 * @Date 2024/5/28 09:04
 * @Version 1.0
 * @Description ArrayUtil
 */
public class ArrayUtil {
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> from) {
        if (CollectionUtil.isEmpty(from)) {
            return (T[]) (new Object[0]);
        }
        return cn.hutool.core.util.ArrayUtil.toArray(from, (Class<T>) IterUtil.getElementType(from.iterator()));
    }

    public static <T> boolean isNonArr(T[] arr) {
        return ObjectUtil.isNull(arr) || arr.length == 0;
    }

    public static <T, U> Set<U> convertSet(T[] arr, Function<T, U> func, Predicate<T> filter) {
        if (isNonArr(arr)) {
            return createEmptySet();
        }
        return Arrays.stream(arr).filter(filter).map(func).collect(Collectors.toSet());
    }

    public static <T, U> Set<U> convertSet(T[] arr, Function<T, U> func) {
        return convertSet(arr, func, Objects::nonNull);
    }

    public static <T, U> List<U> convertList(T[] arr, Function<T, U> func, Predicate<T> filter) {
        if (isNonArr(arr)) {
            return createEmptyList();
        }
        return Arrays.stream(arr).filter(filter).map(func).collect(Collectors.toList());
    }

    public static <T, U> List<U> convertList(T[] arr, Function<T, U> func) {
        return convertList(arr, func, Objects::nonNull);
    }

    public static <T, K> Map<K, T> convertMap(T[] arr, Function<T, K> keyFunc) {
        if (isNonArr(arr)) {
            return new HashMap<>();
        }
        return convertMap(arr, keyFunc, Function.identity());
    }

    public static <T, K, V> Map<K, V> convertMap(T[] arr, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (isNonArr(arr)) {
            return new HashMap<>();
        }
        return convertMap(arr, keyFunc, valueFunc, (v1, v2) -> v1);
    }

    public static <T, K, V> Map<K, V> convertMap(T[] arr, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction) {
        return convertMap(arr, keyFunc, valueFunc, mergeFunction, HashMap::new);
    }

    public static <T, K, V> Map<K, V> convertMap(T[] arr, Function<T, K> keyFunc, Function<T, V> valueFunc, Supplier<? extends Map<K, V>> supplier) {
        if (isNonArr(arr)) {
            return supplier.get();
        }
        return convertMap(arr, keyFunc, valueFunc, (v1, v2) -> v1, supplier);
    }

    public static <T, K, V> Map<K, V> convertMap(T[] arr, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction, Supplier<? extends Map<K, V>> supplier) {
        return Arrays.stream(arr).collect(Collectors.toMap(keyFunc, valueFunc, mergeFunction, supplier));
    }
}
