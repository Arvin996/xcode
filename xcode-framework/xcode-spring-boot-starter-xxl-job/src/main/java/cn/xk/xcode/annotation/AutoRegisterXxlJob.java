package cn.xk.xcode.annotation;

import cn.xk.xcode.entity.ExecutorBlockStrategyEnum;
import cn.xk.xcode.entity.ExecutorRouteStrategyEnum;
import cn.xk.xcode.entity.MisfireStrategyEnum;

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
    ExecutorRouteStrategyEnum executorRouteStrategy() default ExecutorRouteStrategyEnum.FIRST;
    ExecutorBlockStrategyEnum executorBlockStrategy() default ExecutorBlockStrategyEnum.SERIAL_EXECUTION;
    MisfireStrategyEnum misfireStrategy() default MisfireStrategyEnum.DO_NOTHING;
    int executorTimeout() default 0; //单位s
    int executorFailRetryCount() default 0;
    String[] childJobIds() default {};
    String executorParam() default "";
}

