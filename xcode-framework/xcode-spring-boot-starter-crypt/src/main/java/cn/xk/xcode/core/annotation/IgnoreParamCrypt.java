package cn.xk.xcode.core.annotation;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/12/25 10:51
 * @Version 1.0.0
 * @Description IgnoreParamCrypt
 **/
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreParamCrypt {
}
