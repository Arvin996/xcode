package cn.xk.xcode.core.annotation;

import java.lang.annotation.*;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 13:27
 * @description 标记为自动翻译方法
 */
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoTransMethod {
}
