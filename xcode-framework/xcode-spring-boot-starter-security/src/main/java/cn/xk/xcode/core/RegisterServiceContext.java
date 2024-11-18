package cn.xk.xcode.core;

import cn.hutool.core.bean.BeanUtil;
import org.apache.commons.lang3.reflect.MethodUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/11/1 13:49
 * @Version 1.0.0
 * @Description RegisterServiceContext
 **/
public class RegisterServiceContext {
    private static final Map<String, Object> SERVICE_CLASS = new HashMap<>();

    public static void registerService(String serviceName, Object bean) {
        SERVICE_CLASS.put(serviceName, bean);
    }

    public static Object getServiceObject(String serviceName) {
        return SERVICE_CLASS.get(serviceName);
    }

    public static void clear() {
        SERVICE_CLASS.clear();
    }

    public static Object invoke(String serviceName, String methodName, Object object) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        Object bean = getServiceObject(serviceName + "." + methodName);
        Method[] methods = bean.getClass().getDeclaredMethods();
        Class<?> argType = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                argType = method.getParameterTypes()[0];
            }
        }
        return MethodUtils.invokeMethod(getServiceObject(serviceName + "." + methodName), methodName, BeanUtil.toBean(object, argType));
    }
}
