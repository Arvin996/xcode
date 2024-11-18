package cn.xk.xcode.annotation;

import cn.xk.xcode.support.mdc.DefaultUserBizExtraMdcSupportBase;
import cn.xk.xcode.support.mdc.UserBizExtraMdcSupportBase;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/10/17 11:07
 * @Version 1.0.0
 * @Description UserBizLogMdc 使用在类上面或者方法上面
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserBizLogMdc {

    // 订单id
    String orderId() default "";

    // 退款订单id
    String refundId() default "";

    Class<? extends UserBizExtraMdcSupportBase> extraMdcProps() default DefaultUserBizExtraMdcSupportBase.class;
}
