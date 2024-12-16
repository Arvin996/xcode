package cn.xk.xcode.core.aop.proxy;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.ServiceLoaderUtil;
import cn.xk.xcode.core.aop.aspect.Aspect;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author xuk
 * @Date 2024/12/16 11:04
 * @Version 1.0.0
 * @Description ProxyFactory
 **/
@NoArgsConstructor
public abstract class ProxyFactory implements Serializable {

    private static final long serialVersionUID = 1L;

    public abstract <T> T proxy(T target, Aspect aspect);

    public <T> T proxy(T target, Class<? extends Aspect> aspectClass) {
        return proxy(target, ReflectUtil.newInstance(aspectClass));
    }

    public static <T> T createProxy(T target, Class<? extends Aspect> aspectClass) {
        return createProxy(target, ReflectUtil.newInstance(aspectClass));
    }

    public static <T> T createProxy(T target,Aspect aspect) {
        return create().proxy(target, aspect);
    }

    public static ProxyFactory create() {
        return ServiceLoaderUtil.loadFirstAvailable(ProxyFactory.class);
    }
}
