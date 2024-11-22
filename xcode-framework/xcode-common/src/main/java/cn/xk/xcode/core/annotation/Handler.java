package cn.xk.xcode.core.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/11/21 10:27
 * @Version 1.0.0
 * @Description Handler
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Handler {
    @AliasFor(
            annotation = Component.class
    )
    String value() default "";
}
