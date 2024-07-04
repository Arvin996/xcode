package cn.xk.xcode.aop;

import cn.xk.xcode.annotation.BizLogRecord;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * @Author xuk
 * @Date 2024/7/4 14:59
 * @Version 1.0
 * @Description BizLogAnnotationAdvisor
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class BizLogAnnotationAdvisor extends AbstractPointcutAdvisor {

    private final BizLogAnnotationInterceptor advice;

    private final Pointcut pointcut;

    public BizLogAnnotationAdvisor(BizLogAnnotationInterceptor advice){
        this.advice = advice;
        this.pointcut = buildPointcut();
    }

    protected Pointcut buildPointcut() {
        return new AnnotationMatchingPointcut(null, BizLogRecord.class, true);
    }
}
