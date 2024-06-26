package cn.xk.xcode.utils.collections;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.IterUtil;

import java.util.Collection;

/**
 * @Author xuk
 * @Date 2024/5/28 09:04
 * @Version 1.0
 * @Description ArrayUtil
 */
public class ArrayUtil
{
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> from){
        if (CollectionUtil.isEmpty(from)) {
            return (T[]) (new Object[0]);
        }
        return cn.hutool.core.util.ArrayUtil.toArray(from, (Class<T>) IterUtil.getElementType(from.iterator()));
    }
}
