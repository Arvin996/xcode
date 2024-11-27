package cn.xk.xcode.annotation;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import org.springframework.core.annotation.AliasFor;

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

    @AliasFor(annotation = SaCheckPermission.class)
    String type() default "";

    @AliasFor(annotation = SaCheckPermission.class)
    String[] value() default {};

    @AliasFor(annotation = SaCheckPermission.class)
    SaMode mode() default SaMode.AND;

    @AliasFor(annotation = SaCheckPermission.class)
    String[] orRole() default {};
}
