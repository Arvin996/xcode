package cn.xk.xcode.annotation;

import cn.dev33.satoken.annotation.SaCheckPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author xuk
 * @Date 2024/11/21 17:17
 * @Version 1.0.0
 * @Description SaMemberCheckPermission
 **/
@SaCheckPermission(type = "member")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SaMemberCheckPermission {
}
