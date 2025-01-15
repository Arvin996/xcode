package cn.xk.xcode.utils.object;

import cn.hutool.core.util.ClassUtil;
import cn.xk.xcode.core.aop.interceptor.ProxyType;
import cn.xk.xcode.core.aop.proxy.CglibProxyFactory;
import cn.xk.xcode.core.aop.proxy.JdkProxyFactory;
import cn.xk.xcode.core.aop.proxy.ProxyFactory;
import cn.xk.xcode.core.aop.aspect.Aspect;
import cn.xk.xcode.core.aop.proxy.SpringCglibProxyFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @Author xuk
 * @Date 2024/12/16 11:25
 * @Version 1.0.0
 * @Description ProxyUtil
 **/
public class ProxyUtil {

    private ProxyUtil() {
    }

    public static ProxyFactory getProxyFactory(ProxyType proxyType) {
        if (proxyType == ProxyType.CGLIB) {
            return CglibProxyFactory.getInstance();
        } else if (proxyType == ProxyType.JDK) {
            return JdkProxyFactory.getInstance();
        }else {
            return SpringCglibProxyFactory.getInstance();
        }
    }

    public static <T> T proxy(T target, Class<? extends Aspect> aspectClass) {
        return ProxyFactory.createProxy(target, aspectClass);
    }

    public static <T> T proxy(T target, Aspect aspect) {
        return ProxyFactory.createProxy(target, aspect);
    }

    @SuppressWarnings("all")
    public static <T> T newProxyInstance(ClassLoader classloader, InvocationHandler invocationHandler, Class<?>... interfaces) {
        return (T) Proxy.newProxyInstance(classloader, interfaces, invocationHandler);
    }

    public static <T> T newProxyInstance(InvocationHandler invocationHandler, Class<?>... interfaces) {
        return newProxyInstance(ClassUtil.getClassLoader(), invocationHandler, interfaces);
    }

}
