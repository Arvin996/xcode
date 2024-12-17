package cn.xk.xcode.core.aop;

import cn.xk.xcode.core.annotation.TenantIgnore;
import cn.xk.xcode.core.context.TenantContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @Author xuk
 * @Date 2024/12/17 15:27
 * @Version 1.0.0
 * @Description TenantIgnoreAop
 **/
@Aspect
public class TenantIgnoreAop {

    @Around("@annotation(tenantIgnore)")
    public Object around(ProceedingJoinPoint joinPoint, TenantIgnore tenantIgnore) throws Throwable {
        boolean oldIgnore  = TenantContext.isIgnore();
        try {
            TenantContext.setIgnore(true);
            // 执行逻辑
            return joinPoint.proceed();
        }catch (Throwable e){
            throw new Throwable(e);
        }finally {
            TenantContext.setIgnore(oldIgnore);
        }
    }
}
