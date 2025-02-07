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
@Inherited
public @interface AutoRegisterXxlJob {
    // 这个等价于@XxlJob("xxx")中的xxx 如果忘记填了 默认是方法名称
    String executorHandler() default "";

    // 这个等价于@XxlJob(init = "xxx")中的xxx
    String init() default "";

    // 这个等价于@XxlJob(destroy = "xxx")中的xxx
    String destroy() default "";

    String cron();
    String jobDesc();
    String author();
    int triggerStatus() default 0;
    String warnEmail() default "";
    ExecutorRouteStrategyEnum executorRouteStrategy() default ExecutorRouteStrategyEnum.FIRST;
    ExecutorBlockStrategyEnum executorBlockStrategy() default ExecutorBlockStrategyEnum.SERIAL_EXECUTION;
    MisfireStrategyEnum misfireStrategy() default MisfireStrategyEnum.DO_NOTHING;
    int executorTimeout() default 0; //单位s
    int executorFailRetryCount() default 0;
    String[] childJobIds() default {};
    String executorParam() default "";
}

