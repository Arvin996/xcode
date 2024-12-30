package cn.xk.xcode.core.annotation;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/12/30 10:17
 * @Version 1.0.0
 * @Description IgnoreTrans
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreTrans {
}
