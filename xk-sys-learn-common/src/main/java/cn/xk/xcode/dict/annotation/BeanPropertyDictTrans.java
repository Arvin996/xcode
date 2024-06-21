package cn.xk.xcode.dict.annotation;

import cn.xk.xcode.dict.entity.DictCodeMethod;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/5/30 16:27
 * @Version 1.0
 * @Description DictTrans
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BeanPropertyDictTrans
{
    String parentId() default "##";

    @AliasFor("parentId")
    String dictType() default "##";

    String targetField() default "";

    String serviceName() default "base-manage-server";

    DictCodeMethod dictCodeMethod() default DictCodeMethod.LOCAL;
}
