package cn.xk.xcode.utils.object;

import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.xk.xcode.pojo.PageParam;
import cn.xk.xcode.pojo.SortedField;
import org.springframework.util.Assert;

/**
 * @Author xuk
 * @Date 2024/5/28 13:36
 * @Version 1.0
 * @Description PageUtil
 */
public class PageUtil {
    private static final Object[] ORDER_TYPES = new String[]{SortedField.ORDER_ASC, SortedField.ORDER_DESC};

    public static int getStart(PageParam pageParam){
        return (pageParam.getPageNo() - 1) * pageParam.getPageSize();
    }

    /**
     * 构建排序字段
     *
     * @param func  排序字段的 Lambda 表达式
     * @param order 排序类型
     * @param <T>   排序字段所属的类型
     * @return 排序字段
     */
    public static <T> SortedField buildSortedField(Func1<T, ?> func, String order){
        Assert.isTrue(ArrayUtil.contains(ORDER_TYPES, order), String.format("字段的排序类型只能是 %s/%s", ORDER_TYPES));
        String fieldName = LambdaUtil.getFieldName(func);
        return new SortedField(fieldName, order);
    }

    /**
     * 构建排序字段（默认倒序）
     *
     * @param func 排序字段的 Lambda 表达式
     * @param <T>  排序字段所属的类型
     * @return 排序字段
     */
    public static <T> SortedField buildSortedField(Func1<T, ?> func) {
        return buildSortedField(func, SortedField.ORDER_DESC);
    }
}
