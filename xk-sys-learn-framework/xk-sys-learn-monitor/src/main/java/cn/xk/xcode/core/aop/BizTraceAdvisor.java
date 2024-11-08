package cn.xk.xcode.core.aop;

import cn.xk.xcode.core.annotation.BizTrace;
import cn.xk.xcode.core.interceptor.BizTraceInterceptor;
import lombok.Getter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

/**
 * @Author xuk
 * @Date 2024/11/5 10:40
 * @Version 1.0.0
 * @Description BizTraceAdvisor
 **/
@Getter
public class BizTraceAdvisor extends AbstractPointcutAdvisor {

    private final BizTraceInterceptor advice;
    private final Pointcut pointcut;

    public BizTraceAdvisor(BizTraceInterceptor cryptInterceptor) {
        this.advice = cryptInterceptor;
        this.pointcut = bulidPointCut();
    }

    protected Pointcut bulidPointCut() {
        return new AnnotationMatchingPointcut(null, BizTrace.class, true);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
