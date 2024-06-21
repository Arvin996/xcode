package cn.xk.xcode.validation;

import cn.hutool.core.collection.CollUtil;
import cn.xk.xcode.core.IntEnumValueToArray;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/6/6 13:30
 * @Version 1.0
 * @Description InIntEnumCollectionValidator
 */
public class InIntEnumCollectionValidator implements ConstraintValidator<InIntEnum, Collection<Integer>> {

    Set<Integer> values;

    @Override
    public void initialize(InIntEnum constraintAnnotation) {
        IntEnumValueToArray[] enumConstants = constraintAnnotation.value().getEnumConstants();
        if (enumConstants.length == 0){
            values = Collections.emptySet();
        }else {
            values = Arrays.stream(enumConstants[0].toIntArray()).boxed().collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isValid(Collection<Integer> integers, ConstraintValidatorContext constraintValidatorContext) {
        if (values.containsAll(integers)){
            return true;
        }
        // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值）
        constraintValidatorContext.disableDefaultConstraintViolation(); // 禁用默认的 message 的值
        constraintValidatorContext.buildConstraintViolationWithTemplate(constraintValidatorContext.getDefaultConstraintMessageTemplate()
                .replaceAll("\\{value}", CollUtil.join(values, ","))).addConstraintViolation(); // 重新添加错误提示语句
        return false;
    }
}
