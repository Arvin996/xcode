package cn.xk.xcode.core.aop.proxy;

import cn.xk.xcode.core.aop.aspect.Aspect;
import cn.xk.xcode.core.aop.interceptor.JdkInterceptor;
import cn.xk.xcode.utils.object.ProxyUtil;

/**
 * @Author xuk
 * @Date 2024/12/16 11:31
 * @Version 1.0.0
 * @Description JdkProxyFactory
 **/
public class JdkProxyFactory extends ProxyFactory{

    private JdkProxyFactory() {}

    private static final JdkProxyFactory INSTANCE = new JdkProxyFactory();

    public static JdkProxyFactory getInstance() {
        return INSTANCE;
    }
    @Override
    public <T> T proxy(T target, Aspect aspect) {
        return ProxyUtil.newProxyInstance(target.getClass().getClassLoader(), new JdkInterceptor(target, aspect), target.getClass().getInterfaces());
    }
}
