package cn.xk.xcode.annotation;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/12/26 14:40
 * @Version 1.0.0
 * @Description IgnoreTrans 递归
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreRecursionTrans {
}
