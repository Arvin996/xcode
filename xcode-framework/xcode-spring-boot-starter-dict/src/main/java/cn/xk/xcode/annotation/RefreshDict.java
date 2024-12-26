package cn.xk.xcode.annotation;

import org.intellij.lang.annotations.Language;

import java.lang.annotation.*;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/12/26 14:53
 * @Version 1.0.0
 * @Description RefreshDict
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RefreshDict {

    String dictType();

    @Language("SpEL")
    String dictCode();

    @Language("SpEL")
    String dictName();

    @Language("SpEL")
    String dictDesc() default "";
}
