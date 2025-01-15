package cn.xk.xcode.core.aop.proxy;


import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.xk.xcode.core.aop.aspect.Aspect;
import cn.xk.xcode.core.aop.interceptor.CglibInterceptor;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.Constructor;

/**
 * @Author xuk
 * @Date 2024/12/16 11:12
 * @Version 1.0.0
 * @Description CglibProxyFactory
 **/
@SuppressWarnings("unchecked")
public class CglibProxyFactory extends ProxyFactory {

    private static final long serialVersionUID = 1L;

    private CglibProxyFactory() {
    }

    private static final CglibProxyFactory INSTANCE = new CglibProxyFactory();

    public static CglibProxyFactory getInstance() {
        return INSTANCE;
    }

    @Override
    public <T> T proxy(T target, Aspect aspect) {
        Class<?> targetClass = target.getClass();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibInterceptor(target, aspect));
        //      enhancer.create()
        return create(enhancer, targetClass);
    }

    private static <T> T create(Enhancer enhancer, Class<?> targetClass) {
        Constructor<?>[] constructors = ReflectUtil.getConstructors(targetClass);
        IllegalArgumentException finalException = null;
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] values = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                if (ClassUtil.isBasicType(parameterTypes[i])) {
                    values[i] = ClassUtil.getDefaultValue(parameterTypes[i]);
                    continue;
                }
                if (String.class.isAssignableFrom(parameterTypes[i])){
                    values[i] = "default";
                    continue;
                }
                // 如果是实体类型
                if (ClassUtil.isNormalClass(parameterTypes[i])) {}
            }
            try {
                return (T) enhancer.create(parameterTypes, values);
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
