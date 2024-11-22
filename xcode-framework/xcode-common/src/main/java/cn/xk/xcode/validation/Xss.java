package cn.xk.xcode.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author xuk
 * @Date 2024/11/22 16:17
 * @Version 1.0.0
 * @Description Xss
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Constraint(validatedBy = {XssValidator.class})
public @interface Xss {

    String message() default "包含非法脚本字符";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
