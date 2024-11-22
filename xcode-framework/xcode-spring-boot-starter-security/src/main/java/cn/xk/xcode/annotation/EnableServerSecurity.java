package cn.xk.xcode.annotation;

import cn.xk.xcode.core.XcodeSecurityBeanImport;
import cn.xk.xcode.pojo.StpType;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author xuk
 * @Date 2024/11/22 9:36
 * @Version 1.0.0
 * @Description EnableServerSecurity
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import(XcodeSecurityBeanImport.class)
public @interface EnableServerSecurity {

    StpType type();
}
