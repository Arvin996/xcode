package cn.xk.xcode.core.support;

import cn.xk.xcode.core.aop.proxy.CglibProxyFactory;
import cn.xk.xcode.core.aop.proxy.JdkProxyFactory;
import cn.xk.xcode.core.aop.proxy.SpringCglibProxyFactory;
import cn.xk.xcode.core.entity.ProxyType;

/**
 * @Author xuk
 * @Date 2024/12/18 14:21
 * @Version 1.0.0
 * @Description ThirdHttpProxyUtil
 **/
public class ThirdHttpProxyUtil {

    private ThirdHttpProxyUtil() {
    }

    public static <T> T createProxy(T t, BaseThirdRequestAspect baseThirdRequestAspect) {
        return createProxy(ProxyType.SPRING, t, baseThirdRequestAspect);
    }

    public static <T> T createProxy(ProxyType proxyType, T t, BaseThirdRequestAspect baseThirdRequestAspect) {
        if (proxyType == ProxyType.JDK) {
            return JdkProxyFactory.createProxy(t, baseThirdRequestAspect);
        } else if (proxyType == ProxyType.CGLIB) {
            return CglibProxyFactory.createProxy(t, baseThirdRequestAspect);
        } else {
            return SpringCglibProxyFactory.createProxy(t, baseThirdRequestAspect);
        }
    }
}
