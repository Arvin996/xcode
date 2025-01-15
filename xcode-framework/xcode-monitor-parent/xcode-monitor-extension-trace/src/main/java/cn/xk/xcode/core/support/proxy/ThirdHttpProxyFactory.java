package cn.xk.xcode.core.support.proxy;

import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.config.TraceConfiguration;
import cn.xk.xcode.core.aop.interceptor.ProxyType;
import cn.xk.xcode.utils.object.ProxyUtil;

import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/12/18 14:21
 * @Version 1.0.0
 * @Description ThirdHttpProxyFactory
 **/
public class ThirdHttpProxyFactory {

    private ThirdHttpProxyFactory() {
    }

    public static <T> T createProxy(T t, AbstractThirdRequestAspect abstractThirdRequestAspect) {
        return createProxy(ProxyType.SPRING, t, abstractThirdRequestAspect);
    }

    public static <T> T createProxy(ProxyType proxyType, T t, AbstractThirdRequestAspect abstractThirdRequestAspect) {
        TraceConfiguration bean = SpringUtil.getBean(TraceConfiguration.class);
        if (Objects.isNull(bean)) {
            return t;
        }
        return ProxyUtil.getProxyFactory(proxyType).proxy(t, abstractThirdRequestAspect);
    }
}
