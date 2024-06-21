package cn.xk.xcode.dict.annotation;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/5/31 09:19
 * @Version 1.0
 * @Description DictTrans
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DictTrans {
}
