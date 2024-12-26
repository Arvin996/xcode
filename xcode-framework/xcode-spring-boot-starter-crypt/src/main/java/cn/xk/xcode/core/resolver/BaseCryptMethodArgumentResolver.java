package cn.xk.xcode.core.resolver;

import cn.xk.xcode.core.annotation.IgnoreCrypt;
import org.springframework.core.MethodParameter;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/12/26 9:57
 * @Version 1.0.0
 * @Description BaseCryptMethodArgumentResolver
 **/
public interface BaseCryptMethodArgumentResolver {

    default boolean deterDecrypt(MethodParameter parameter){
        Method method = parameter.getMethod();
        if (Objects.nonNull(method)){
            Class<?> clazz = method.getDeclaringClass();
            if (clazz.isAnnotationPresent(IgnoreCrypt.class)){
                return false;
            }
            if (method.isAnnotationPresent(IgnoreCrypt.class)){
                return false;
            }
        }
        return true;
    }
}
