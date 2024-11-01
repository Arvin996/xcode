package cn.xk.xcode.annotation;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/11/1 13:46
 * @Version 1.0.0
 * @Description AutoRegisterController
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoRegisterController {
    String name() default "";
}
