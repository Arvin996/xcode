package cn.xk.xcode.aop;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

/**
 * @Author xuk
 * @Date 2024/10/17 11:13
 * @Version 1.0.0
 * @Description UserBizLogAdvisor
 **/
@Getter
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class UserBizLogMdcAdvisor extends AbstractPointcutAdvisor {

    private final UserBizLogMdcInterceptor advice;

    private final Pointcut pointcut;

    public UserBizLogMdcAdvisor(UserBizLogMdcInterceptor advice) {
        this.advice = advice;
        this.pointcut = bulidPointCut();
    }

    protected Pointcut bulidPointCut() {
        AspectJExpressionPointcut expressionPointcut = new AspectJExpressionPointcut();
        expressionPointcut.setExpression("execution(* cn.xk.xcode.controller..*.*(..))");
        return expressionPointcut;
    }

    @Override
    public int getOrder(){
        return -1;
    }

}
