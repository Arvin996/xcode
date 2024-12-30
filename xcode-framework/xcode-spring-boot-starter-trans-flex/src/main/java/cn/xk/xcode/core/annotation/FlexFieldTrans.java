package cn.xk.xcode.core.annotation;

import cn.xk.xcode.core.entity.TransPo;

import java.lang.annotation.*;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 13:26
 * @description FlexFieldTrans
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FlexFieldTrans {
    /**
     * 用于entity查询的实体类型
     * @return 实体类型
     */
    Class<? extends TransPo> ref();

    /**
     * 要给哪个字段做翻译
     * @return 字段名
     */
    String targetField();

    /**
     * 当前的属性在Wrapper中的where条件
     * 如现在源头是schoolId,那么这里就是id
     * @return 实际条件字段名
     */
    String refConditionField();

    /**
     * Wrapper查询出来实体类后，要取哪个属性进行返回翻译
     * @return 属性名
     */
    String refSourceFiled();

    /**
     * 是否是远程调用
     * @return true 是 false 否
     */
    boolean isRpc() default false;

    /**
     * 远程调用时需要
     * @return 服务名
     */
    String serviceName() default "";
}
