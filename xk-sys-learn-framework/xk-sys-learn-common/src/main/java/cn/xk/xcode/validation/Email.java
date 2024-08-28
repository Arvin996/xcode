package cn.xk.xcode.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author Administrator
 * @Date 2024/8/27 14:06
 * @Version V1.0.0
 * @Description Email
 */
@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = EmailValidator.class
)
public @interface Email {

    String message() default "邮箱格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
