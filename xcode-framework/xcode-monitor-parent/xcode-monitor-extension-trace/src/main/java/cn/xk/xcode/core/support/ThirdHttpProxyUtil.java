package cn.xk.xcode.core.support;

import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.config.TraceConfiguration;
import cn.xk.xcode.core.aop.proxy.CglibProxyFactory;
import cn.xk.xcode.core.aop.proxy.JdkProxyFactory;
import cn.xk.xcode.core.aop.proxy.SpringCglibProxyFactory;
import cn.xk.xcode.core.entity.ProxyType;

import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/12/18 14:21
 * @Version 1.0.0
 * @Description ThirdHttpProxyUtil
 **/
public class ThirdHttpProxyUtil {

    private ThirdHttpProxyUtil() {
    }

    public static <T> T createProxy(T t, AbstractThirdRequestAspect abstractThirdRequestAspect) {
        return createProxy(ProxyType.SPRING, t, abstractThirdRequestAspect);
    }

    public static <T> T createProxy(ProxyType proxyType, T t, AbstractThirdRequestAspect abstractThirdRequestAspect) {
        TraceConfiguration bean = SpringUtil.getBean(TraceConfiguration.class);
        if (Objects.isNull(bean)){
            return t;
        }
        if (proxyType == ProxyType.JDK) {
            return JdkProxyFactory.createProxy(t, abstractThirdRequestAspect);
        } else if (proxyType == ProxyType.CGLIB) {
            return CglibProxyFactory.createProxy(t, abstractThirdRequestAspect);
        } else {
            return SpringCglibProxyFactory.createProxy(t, abstractThirdRequestAspect);
        }
    }
}
