package cn.xk.xcode.annotation;

import cn.xk.xcode.entity.DictTranType;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/5/30 16:27
 * @Version 1.0
 * @Description DictFieldTrans 这个注解只用在String 或者基本类型参数上 用于字典翻译
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictFieldTrans {

    String dictType();

    String targetField();

    String serviceName() default "";

    DictTranType dictTranType() default DictTranType.LOCAL;
}
