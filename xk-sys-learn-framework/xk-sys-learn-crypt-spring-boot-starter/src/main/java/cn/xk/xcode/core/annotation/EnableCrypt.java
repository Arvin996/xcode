package cn.xk.xcode.core.annotation;

import cn.xk.xcode.config.CryptImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/10/26 12:17
 * @description EnableCrypt
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(CryptImportSelector.class)
public @interface EnableCrypt {
    boolean isSign() default false;
}
