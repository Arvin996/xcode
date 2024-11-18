package cn.xk.xcode.utils.collections;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/5/28 09:09
 * @Version 1.0
 * @Description CollectionUtil
 */
public class CollectionUtil {
    public static <T> boolean anyMatch(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().anyMatch(predicate);
    }


    public static boolean containsAny(Object source, Object... target) {
        return Arrays.asList(target).contains(source);
    }

    public static <T> List<T> filterAsList(Collection<T> collection, Predicate<T> predicate) {
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }
        return collection.stream().filter(predicate).collect(Collectors.toList());
    }

    public static <T, R> List<T> distinctAsList(Collection<T> collection, Function<T, R> keyMapper) {
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(convertMap(collection, keyMapper, Function.identity()).values());
    }

    public static <T, R> List<T> distinctAsList(Collection<T> collection, Function<T, R> keyMapper, BinaryOperator<T> cover) {
        if (collection.isEmpty()) {
            return Collections.emptyList();
        }
        return new ArrayList<>(convertMap(collection, keyMapper, Function.identity(), cover).values());
    }

    public static <T, K> Map<K, T> convertMap(Collection<T> from, Function<T, K> keyFunc) {
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from, keyFunc, Function.identity());
    }

    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return convertMap(from, keyFunc, valueFunc, (v1, v2) -> v1);
    }

    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction) {
        return convertMap(from, keyFunc, valueFunc, mergeFunction, HashMap::new);
    }

    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, Supplier<? extends Map<K, V>> supplier) {
        if (CollUtil.isEmpty(from)) {
            return supplier.get();
        }
        return convertMap(from, keyFunc, valueFunc, (v1, v2) -> v1, supplier);
    }

    public static <T, K, V> Map<K, V> convertMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc, BinaryOperator<V> mergeFunction, Supplier<? extends Map<K, V>> supplier) {
        return from.stream().collect(Collectors.toMap(keyFunc, valueFunc, mergeFunction, supplier));
    }

    public static <T, R> Set<R> convertSet(Collection<T> from, Function<T, R> func) {
        return from.stream().map(func).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public static <T, U> Set<U> convertSet(Collection<T> from, Function<T, U> func, Predicate<T> filter) {
        return from.stream().filter(filter).map(func).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    public static <T, K> Map<K, T> convertMapByFilter(Collection<T> from, Predicate<T> filter, Function<T, K> keyFunc) {
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream().filter(filter).collect(Collectors.toMap(keyFunc, Function.identity()));
    }

    public static <T, K> Map<K, List<T>> convertMultiMap(Collection<T> from, Function<T, K> keyFunc) {
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream().collect(Collectors.groupingBy(keyFunc, Collectors.mapping(Function.identity(), Collectors.toList())));
    }

    public static <T, K, V> Map<K, List<V>> convertMultiMap(Collection<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        if (CollUtil.isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream().collect(Collectors.groupingBy(keyFunc, Collectors.mapping(valueFunc, Collectors.toList())));
    }

    public static <T> List<List<T>> diffList(Collection<T> oldList, Collection<T> newList,
                                             BiFunction<T, T, Boolean> sameFunc) {
        List<T> createList = new LinkedList<>(newList); // 默认都认为是新增的，后续会进行移除
        List<T> updateList = new ArrayList<>();
        List<T> deleteList = new ArrayList<>();
        for (T t : oldList) {
            T foundObj = null;
            for (Iterator<T> iterator = createList.iterator(); iterator.hasNext(); ) {
                T createObj = iterator.next();
                if (sameFunc.apply(t, createObj)) {
                    foundObj = createObj;
                    iterator.remove();
                    break;
                }
            }
            if (foundObj != null) {
                updateList.add(foundObj);
            } else {
                deleteList.add(t);
            }
        }
        return Arrays.asList(createList, updateList, deleteList);
    }

    public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
        return CollectionUtils.containsAny(source, candidates);
    }

    public static <T> T getFirst(List<T> from) {
        return from.isEmpty() ? null : from.get(0);
    }

    public static <T> T findFirst(Collection<T> from, Predicate<T> predicate) {
        return findFirst(from, predicate, Function.identity());
    }

    public static <T, U> U findFirst(Collection<T> from, Predicate<T> predicate, Function<T, U> func) {
        return from.stream().filter(predicate).map(func).findFirst().orElse(null);
    }

    public static <T, V extends Comparable<? super V>> V getMaxValue(Collection<T> from, Function<T, V> valueFunc) {
        if (CollUtil.isEmpty(from)) {
            return null;
        }
        assert !from.isEmpty(); // 断言，避免告警
        T t = from.stream().max(Comparator.comparing(valueFunc)).get();
        return valueFunc.apply(t);
    }

    public static <T, V extends Comparable<? super V>> V getMinValue(List<T> from, Function<T, V> valueFunc) {
        if (CollUtil.isEmpty(from)) {
            return null;
        }
        assert !from.isEmpty(); // 断言，避免告警
        T t = from.stream().min(Comparator.comparing(valueFunc)).get();
        return valueFunc.apply(t);
    }

    public static <T, V extends Comparable<? super V>> V getSumValue(List<T> from, Function<T, V> valueFunc,
                                                                     BinaryOperator<V> accumulator) {
        if (CollUtil.isEmpty(from)) {
            return null;
        }
        return getSumValue(from, valueFunc, accumulator, null);
    }

    public static <T, V extends Comparable<? super V>> V getSumValue(Collection<T> from, Function<T, V> valueFunc,
                                                                     BinaryOperator<V> accumulator, V defaultValue) {
        if (CollUtil.isEmpty(from)) {
            return defaultValue;
        }
        return from.stream().map(valueFunc).reduce(defaultValue, accumulator);
    }

    public static <T> void addIfNotNull(Collection<T> coll, T item) {
        if (item != null) {
            coll.add(item);
        }
    }

    public static <T> Collection<T> singleton(T obj) {
        return Objects.isNull(obj) ? Collections.emptyList() : Collections.singletonList(obj);
    }

    public static <T> List<T> newArrayList(List<List<T>> list) {
        return list.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public static <T, U> List<U> convertList(Collection<T> from, Function<T, U> func) {
        return convertList(from, func, Objects::nonNull);
    }

    public static <T, U> List<U> convertList(Collection<T> from, Function<T, U> func, Predicate<T> filter) {
        if (CollUtil.isEmpty(from)) {
            return new ArrayList<>();
        }
        return from.stream().filter(filter).map(func).collect(Collectors.toList());
    }


    public static <T> List<T> createEmptyList() {
        return Collections.emptyList();
    }

    public static <T> Set<T> createEmptySet() {
        return Collections.emptySet();
    }

    public static <K, V> Map<K, V> createEmptyMap() {
        return Collections.emptyMap();
    }

    public static <T> List<T> createSingleList(T obj) {
        return Collections.singletonList(obj);
    }

    public static <T> Set<T> createSingleSet(T obj) {
        return Collections.singleton(obj);
    }

    public static <K, V> Map<K, V> createSingleMap(K key, V value) {
        return Collections.singletonMap(key, value);
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isNotEmpty(Collection<T> collection) {
        return !isEmpty(collection);
    }
}
