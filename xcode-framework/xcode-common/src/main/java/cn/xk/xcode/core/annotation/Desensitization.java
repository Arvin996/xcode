package cn.xk.xcode.core.annotation;

import cn.xk.xcode.desensitization.DesensitizationStrange;
import cn.xk.xcode.desensitization.JacksonDataDesensitized;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author xuk
 * @Date 2024/11/22 15:02
 * @Version 1.0.0
 * @Description Desensitization 数据脱敏注解
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = JacksonDataDesensitized.class)
public @interface Desensitization {

    DesensitizationStrange strange();
}
