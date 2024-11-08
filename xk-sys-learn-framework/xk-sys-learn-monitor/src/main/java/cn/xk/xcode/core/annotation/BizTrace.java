package cn.xk.xcode.core.annotation;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/11/5 10:00
 * @Version 1.0.0
 * @Description BizTrace
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface BizTrace {

    /**
     * 业务编号 tag 名
     */
    String ID_TAG = "biz.id";
    /**
     * 业务类型 tag 名
     */
    String TYPE_TAG = "biz.type";

    // 业务类型
    String bizType();

    // 业务id
    String bizId() default  "";

    // 操作名称
    String operationName() default "";
}
