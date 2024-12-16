package cn.xk.xcode.core.aop.aspect;

import java.lang.reflect.Method;

/**
 * @Author xuk
 * @Date 2024/12/16 10:26
 * @Version 1.0.0
 * @Description Aspect
 **/
public interface Aspect {

    boolean before(Object var1, Method var2, Object[] var3);

    boolean after(Object var1, Method var2, Object[] var3, Object var4);

    boolean afterException(Object var1, Method var2, Object[] var3, Throwable var4);
}
