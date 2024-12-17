package cn.xk.xcode.core.annotation;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/12/17 15:27
 * @Version 1.0.0
 * @Description TenantIgnore
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TenantIgnore {
}
