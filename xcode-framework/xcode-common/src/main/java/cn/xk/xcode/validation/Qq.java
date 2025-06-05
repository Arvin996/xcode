package cn.xk.xcode.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2025/6/4 9:58
 * @Version 1.0.0
 * @Description Qq
 **/
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
        validatedBy = QqValidator.class
)
public @interface Qq {

    String message() default "qq格式不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
