package cn.xk.xcode.annotation;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author xuk
 * @Date 2024/11/21 17:18
 * @Version 1.0.0
 * @Description SaMemberCheckRole
 **/
@SaCheckRole(type = "member")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SaMemberCheckRole {

    @AliasFor(annotation = SaCheckRole.class)
    String[] value() default {};

    @AliasFor(annotation = SaCheckRole.class)
    SaMode mode() default SaMode.AND;
}
