package cn.xk.xcode.core.aop;

import cn.xk.xcode.core.annotation.UseAiPlatform;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * @Author xuk
 * @Date 2025/2/14 14:33
 * @Version 1.0.0
 * @Description AiPlatformAdvisor
 **/
@Getter
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class AiPlatformAdvisor extends AbstractPointcutAdvisor {

    private final AiPlatformInterceptor advice;
    private final Pointcut pointcut;

    public AiPlatformAdvisor(AiPlatformInterceptor advice) {
        this.advice = advice;
        this.pointcut = buildPointcut();
    }

    protected Pointcut buildPointcut() {
        // 分别两个pointcut，一个是类上的，一个是方法上的
        Pointcut classPointcut = new AnnotationMatchingPointcut(UseAiPlatform.class,null, true);
        Pointcut methodPointcut = new AnnotationMatchingPointcut(null, UseAiPlatform.class, true);
        // 使用 ComposablePointcut 进行组合 表示这个pointcut切点key可以是类上的或者方法上的
        return new ComposablePointcut(classPointcut).union(methodPointcut);
    }
}
