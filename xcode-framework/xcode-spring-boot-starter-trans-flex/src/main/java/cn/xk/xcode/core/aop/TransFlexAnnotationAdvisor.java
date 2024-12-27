package cn.xk.xcode.core.aop;

import cn.xk.xcode.core.annotation.AutoFlexTrans;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.aopalliance.aop.Advice;
/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 13:58
 * @description
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class TransFlexAnnotationAdvisor extends AbstractPointcutAdvisor {

    private final transient Pointcut pointcut;
    private final transient Advice advice;

    public TransFlexAnnotationAdvisor(TransFlexAnnotationInterceptor advice){
        this.advice = advice;
        this.pointcut = buildPointcut();
    }

    protected Pointcut buildPointcut() {
        Pointcut classPointcut = new AnnotationMatchingPointcut(AutoFlexTrans.class, true);
        Pointcut methodPointcut = new AnnotationMatchingPointcut(null, AutoFlexTrans.class, true);
        return new ComposablePointcut(classPointcut).union(methodPointcut);
    }
}
