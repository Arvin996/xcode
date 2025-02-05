package cn.xk.xcode.annotation;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2025/2/5 11:16
 * @Version 1.0.0
 * @Description AutoRegisterXxlJob
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutoRegisterXxlJob {
    String cron();
    String jobDesc();
    String author();
    String warnEmail() default "";
}

