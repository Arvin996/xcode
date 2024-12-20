package cn.xk.xcode.permission.aop;

import cn.xk.xcode.permission.annotation.DataPermission;
import cn.xk.xcode.permission.context.DatePermissionHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/12/19 14:14
 * @Version 1.0.0
 * @Description DatePermissionAspect
 **/
@Aspect
@Slf4j
public class DatePermissionAspect {

    @Around(value = "@annotation(cn.xk.xcode.permission.annotation.DataPermission) || @within(cn.xk.xcode.permission.annotation.DataPermission)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        DataPermission dataPermission = getAnnotation(joinPoint);
        if (Objects.nonNull(dataPermission)) {
            DatePermissionHolder.add(dataPermission.scope(), dataPermission.tableClazz());
        }
        try {
            // 执行逻辑
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new Throwable(throwable);
        }finally {
            // 出栈
            if (dataPermission != null) {
                DatePermissionHolder.remove();
            }
        }
    }

    private DataPermission getAnnotation(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object target = joinPoint.getThis();
        Class<?> clazz = Objects.isNull(target) ? method.getDeclaringClass() : target.getClass();
        // 类上面会覆盖方法
        DataPermission dataPermission = AnnotationUtils.getAnnotation(method, DataPermission.class);
        if (Objects.isNull(dataPermission)) {
            dataPermission = AnnotationUtils.getAnnotation(clazz, DataPermission.class);
        }
        return dataPermission;
    }
}
