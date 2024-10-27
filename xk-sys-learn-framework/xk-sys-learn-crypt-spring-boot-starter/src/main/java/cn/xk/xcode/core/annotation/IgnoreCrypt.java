package cn.xk.xcode.core.annotation;

import java.lang.annotation.*;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/10/26 12:40
 * @description IgnoreCrypt
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreCrypt {

}
