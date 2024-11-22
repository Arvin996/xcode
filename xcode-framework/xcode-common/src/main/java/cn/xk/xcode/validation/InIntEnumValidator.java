package cn.xk.xcode.validation;

import cn.xk.xcode.core.annotation.IntEnumValueToArray;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/6/6 13:29
 * @Version 1.0
 * @Description InIntEnumValidator
 */
public class InIntEnumValidator implements ConstraintValidator<InIntEnum, Integer> {

    private List<Integer> values;

    @Override
    public void initialize(InIntEnum constraintAnnotation) {
        IntEnumValueToArray[] enumConstants = constraintAnnotation.value().getEnumConstants();
        if (enumConstants.length == 0){
            values = Collections.emptyList();
        }else {
            values = Arrays.stream(enumConstants[0].toIntArray()).boxed().collect(Collectors.toList());
        }
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if (values.contains(integer)){
            return true;
        }
        // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值）
        constraintValidatorContext.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
        constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{value}", values.toString())).addConstraintViolation(); // 重新添加错误提示语句
        return false;
    }
}
