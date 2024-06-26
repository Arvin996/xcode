package cn.xk.xcode.utils.object;

import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.utils.collections.CollectionUtil;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * @Author xuk
 * @Date 2024/5/28 13:16
 * @Version 1.0
 * @Description BeanUtil
 */
public class BeanUtil
{
    public static <T> T toBean(Object source, Class<T> targetClass){
        return cn.hutool.core.bean.BeanUtil.toBean(source, targetClass);
    }

    public static <T> T toBean(Object source, Class<T> targetClass, Consumer<T> peek){
        T bean = toBean(source, targetClass);
        peek.accept(bean);
        return bean;
    }

    public static <S, T> List<T> toBean(List<S> source, Class<T> targetType){
        if (source.isEmpty()){
            return Collections.emptyList();
        }
        return CollectionUtil.convertList(source, s -> toBean(s, targetType));
    }

    public static <S, T> List<T> toBean(List<S> source, Class<T> targetType, Consumer<T> peek){
        List<T> beans = toBean(source, targetType);
        beans.forEach(peek);
        return beans;
    }

    public static <S, T> PageResult<T> toBean(PageResult<S> source, Class<T> targetType){
        return toBean(source, targetType, null);
    }

    public static <S, T> PageResult<T> toBean(PageResult<S> source, Class<T> targetType, Consumer<T> peek){
        List<T> beans = toBean(source.getData(), targetType);
        beans.forEach(peek);
        return new PageResult<>(source.getTotal(), beans);
    }
}
