package cn.xk.xcode.core.aop;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/10/26 12:33
 * @description CryptAdvisor
 */
@Getter
@RequiredArgsConstructor
public class CryptAdvisor extends AbstractPointcutAdvisor {

    private final CryptInterceptor advice;
    private final Pointcut pointcut;

    public CryptAdvisor(CryptInterceptor cryptInterceptor) {
        this.advice = cryptInterceptor;
        this.pointcut = bulidPointCut();
    }

    protected Pointcut bulidPointCut() {
        AspectJExpressionPointcut expressionPointcut = new AspectJExpressionPointcut();
        expressionPointcut.setExpression("execution(* cn.xk.xcode.controller..*.*(..))");
        return expressionPointcut;
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
