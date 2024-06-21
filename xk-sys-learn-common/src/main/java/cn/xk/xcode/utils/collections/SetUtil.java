package cn.xk.xcode.utils.collections;

import cn.hutool.core.collection.CollUtil;

import java.util.Set;

/**
 * @Author xuk
 * @Date 2024/5/28 09:09
 * @Version 1.0
 * @Description SetUtil
 */
public class SetUtil
{
    @SafeVarargs
    public static <T> Set<T> asSet(T... objs) {
        return CollUtil.newHashSet(objs);
    }
}
