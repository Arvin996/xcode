package cn.xk.xcode.annotation;

import cn.xk.xcode.core.RegisterControllerImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/11/1 13:53
 * @Version 1.0.0
 * @Description EnableRegisterController
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(RegisterControllerImportSelector.class)
public @interface EnableRegisterController {
    String basePackage() default "";
}
