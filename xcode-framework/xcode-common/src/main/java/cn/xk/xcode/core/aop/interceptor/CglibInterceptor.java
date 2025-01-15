package cn.xk.xcode.core.aop.interceptor;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.xk.xcode.core.aop.aspect.Aspect;
import lombok.Getter;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @Author xuk
 * @Date 2024/12/16 10:33
 * @Version 1.0.0
 * @Description CglibInterceptor
 **/
@Getter
public class CglibInterceptor implements MethodInterceptor, Serializable {

    private static final long serialVersionUID = 1L;
    private final Object target;
    private final Aspect aspect;

    public CglibInterceptor(Object target, Aspect aspect) {
        this.target = target;
        this.aspect = aspect;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        Object target = this.target;
        Object result = null;
        if (this.aspect.before(target, method, args)) {
            try {
                result = proxy.invoke(target, args);
            } catch (Throwable e) {
                Throwable throwable = e;
                if (e instanceof InvocationTargetException) {
                    throwable = ((InvocationTargetException)e).getTargetException();
                }

                if (this.aspect.afterException(target, method, args, throwable)) {
                    throw throwable;
                }
            }
        }

        return this.aspect.after(target, method, args, result) ? result : null;
    }
}
