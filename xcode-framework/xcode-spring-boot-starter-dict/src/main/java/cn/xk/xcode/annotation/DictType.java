package cn.xk.xcode.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author xuk
 * @Date 2024/7/4 16:05
 * @Version 1.0
 * @Description DictType 作用于枚举类上 注册进字典
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DictType
{
    String dictType();

    String dictName();
}
