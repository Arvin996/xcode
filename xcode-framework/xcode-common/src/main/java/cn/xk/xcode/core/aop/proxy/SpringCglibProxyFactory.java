package cn.xk.xcode.core.aop.proxy;


import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.xk.xcode.core.aop.aspect.Aspect;
import cn.xk.xcode.core.aop.interceptor.SpringCglibInterceptor;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Constructor;

/**
 * @Author xuk
 * @Date 2024/12/16 11:23
 * @Version 1.0.0
 * @Description SpringCglibProxyFactory
 **/
@SuppressWarnings("unchecked")
public class SpringCglibProxyFactory extends ProxyFactory {

    private static final long serialVersionUID = 1L;

    private SpringCglibProxyFactory() {
    }

    private static final SpringCglibProxyFactory INSTANCE = new SpringCglibProxyFactory();

    public static SpringCglibProxyFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> T proxy(T target, Aspect aspect) {
        Class<?> targetClass = target.getClass();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetClass);
        enhancer.setCallback(new SpringCglibInterceptor(target, aspect));
        return create(enhancer, targetClass);
    }

    private static <T> T create(Enhancer enhancer, Class<?> targetClass) {
        Constructor<?>[] constructors = ReflectUtil.getConstructors(targetClass);
        IllegalArgumentException finalException = null;

        for(Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] values = ClassUtil.getDefaultValues(parameterTypes);

            try {
                return (T)enhancer.create(parameterTypes, values);
            } catch (IllegalArgumentException e) {
                finalException = e;
            }
        }

        if (null != finalException) {
            throw finalException;
        } else {
            throw new IllegalArgumentException("No constructor provided");
        }
    }
}
