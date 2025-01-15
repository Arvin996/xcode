package cn.xk.xcode.core.aop.interceptor;


import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.xk.xcode.core.aop.aspect.Aspect;
import lombok.Getter;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author xuk
 * @Date 2024/12/16 10:49
 * @Version 1.0.0
 * @Description JdkInterceptor
 **/
@Getter
public class JdkInterceptor implements InvocationHandler, Serializable {

    private static final long serialVersionUID = 1L;
    private final Object target;
    private final Aspect aspect;

    public JdkInterceptor(Object target, Aspect aspect) {
        this.target = target;
        this.aspect = aspect;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object target = this.target;
        Aspect aspect = this.aspect;
        Object result = null;
        if (aspect.before(target, method, args)) {
            ReflectUtil.setAccessible(method);

            try {
                result = method.invoke(ClassUtil.isStatic(method) ? null : target, args);
            } catch (InvocationTargetException e) {
                if (aspect.afterException(target, method, args, e.getTargetException())) {
                    throw e;
                }
            }

            if (aspect.after(target, method, args, result)) {
                return result;
            }
        }

        return null;
    }
}
