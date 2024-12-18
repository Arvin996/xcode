package cn.xk.xcode.core.annotation;

import java.lang.annotation.*;

/**
* @Author xuk
* @Date 2024/12/18 11:22
* @Version 1.0.0
* @Description ThirdApi 标记一个接口为三方服务调用的 此时不会在请求头中传递traceId
**/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ThirdApi {
    /**
     *  是否框架自动生成traceId 默认true
     *   如果设置成false 请在方法逻辑中参考{@link cn.xk.xcode.core.context.TraceContextHolder}，调用其setTraceId("xxx")方法
     */
    boolean autoGenTraceId() default true;
}
