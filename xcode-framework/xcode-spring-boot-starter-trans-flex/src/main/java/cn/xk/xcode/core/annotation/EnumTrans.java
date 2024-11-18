package cn.xk.xcode.core.annotation;

import java.lang.annotation.*;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 9:32
 * @description
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnumTrans {
    /**
     * 目标属性
     * @return 目标属性
     */
    String targetField();

    /**
     * 枚举类型
     * @return 枚举类型
     */
    String enumType();

    /**
     * 是否是远程调用
     * @return true 是 false 否
     */
    boolean isRpc() default false;

    /**
     * 远程调用时需要
     * @return 服务名
     */
    String serviceName();
}
