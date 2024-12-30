package cn.xk.xcode.core.annotation;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/12/30 9:54
 * @Version 1.0.0
 * @Description IgnoreRecFlexTrans
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreRecFlexTrans {
}
