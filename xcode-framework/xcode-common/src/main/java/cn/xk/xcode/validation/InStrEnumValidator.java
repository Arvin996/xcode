package cn.xk.xcode.validation;

import cn.xk.xcode.core.annotation.StringEnumValueToArray;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/6/6 13:38
 * @Version 1.0
 * @Description InStrEnumValidator
 */
public class InStrEnumValidator implements ConstraintValidator<InStrEnum, String> {

    Set<String> set;

    @Override
    public void initialize(InStrEnum constraintAnnotation) {
        StringEnumValueToArray[] enumConstants = constraintAnnotation.value().getEnumConstants();
        if (enumConstants.length == 0){
            set = Collections.emptySet();
        }else {
            set = Arrays.stream(enumConstants[0].toArrayString()).collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (set.contains(s)){
            return true;
        }
        // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值）
        constraintValidatorContext.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
        constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{value}", set.toString())).addConstraintViolation(); // 重新添加错误提示语句
        return false;
    }
}
