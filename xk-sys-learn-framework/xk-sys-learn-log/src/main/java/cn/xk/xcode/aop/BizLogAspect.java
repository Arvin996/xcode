package cn.xk.xcode.aop;

import cn.xk.xcode.entity.BizAccessLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/7/2 20:01
 * @description
 */
@Component
@Aspect
public class BizLogAspect {
    @Pointcut("(execution(public * cn.xk..*.controller..*.*(..)))")
    public void methodArgs() {
    }

    // todo 扩展 支持spel表达式
    @Around("methodArgs()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        return proceed;
    }

    public void before(BizAccessLog bizAccessLog, ProceedingJoinPoint joinPoint){}

    public void after(BizAccessLog bizAccessLog, ProceedingJoinPoint joinPoint){}
}
