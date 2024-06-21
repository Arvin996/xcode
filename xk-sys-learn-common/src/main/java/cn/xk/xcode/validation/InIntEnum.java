package cn.xk.xcode.validation;

import cn.xk.xcode.core.EnumValueToArray;
import cn.xk.xcode.core.IntEnumValueToArray;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

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
        validatedBy = {InIntEnumValidator.class, InIntEnumCollectionValidator.class}
)
public @interface InIntEnum {

    /**
     * @return 实现 EnumValuable 接口的
     */
    Class<? extends IntEnumValueToArray> value();

    String message() default "必须在指定范围 {value}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
