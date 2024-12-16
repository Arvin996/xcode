package cn.xk.xcode.core.aop.interceptor;

import cn.xk.xcode.core.aop.aspect.Aspect;
import lombok.Getter;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author xuk
 * @Date 2024/12/16 11:01
 * @Version 1.0.0
 * @Description SpringCglibInterceptor
 **/
@Getter
public class SpringCglibInterceptor implements MethodInterceptor, Serializable {

    private static final long serialVersionUID = 1L;
    private final Object target;
    private final Aspect aspect;

    public SpringCglibInterceptor(Object target, Aspect aspect) {
        this.target = target;
        this.aspect = aspect;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object target = this.target;
        Object result = null;
        if (this.aspect.before(target, method, objects)) {
            try {
                result = methodProxy.invoke(target, objects);
            } catch (Throwable e) {
                Throwable throwable = e;
                if (e instanceof InvocationTargetException) {
                    throwable = ((InvocationTargetException)e).getTargetException();
                }

                if (this.aspect.afterException(target, method, objects, throwable)) {
                    throw e;
                }
            }
        }
        return this.aspect.after(target, method, objects, result) ? result : null;
    }
}
