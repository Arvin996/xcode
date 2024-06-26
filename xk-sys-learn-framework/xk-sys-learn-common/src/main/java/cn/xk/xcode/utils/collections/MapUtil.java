package cn.xk.xcode.utils.collections;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;

import java.util.*;
import java.util.function.Consumer;

/**
 * @Author xuk
 * @Date 2024/5/28 09:09
 * @Version 1.0
 * @Description MapUtil
 */
public class MapUtil
{
    public static <K, V> List<V> getList(Map<K, V> multimap, Collection<K> keys){
        List<V> list = Collections.emptyList();
        keys.stream().forEach(key -> {
            list.add(multimap.get(key));
        });
        return list;
    }

    public static <K, V> void findAndThen(Map<K, V> map, K key, Consumer<V> consumer){
        if (ObjUtil.isNull(key) || CollUtil.isEmpty(map)) {
            return;
        }
        V v = map.get(key);
        if (Objects.isNull(v)){
            return;
        }
        consumer.accept(v);
    }
}
