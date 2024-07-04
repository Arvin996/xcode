package cn.xk.xcode.annotation;

import org.intellij.lang.annotations.Language;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/7/4 12:57
 * @Version 1.0
 * @Description LogRecod
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BizLogRecord
{
    @Language("SpEL")
    String bizId() default "";

    String bizType() default "";

    String desc() default "";
}
