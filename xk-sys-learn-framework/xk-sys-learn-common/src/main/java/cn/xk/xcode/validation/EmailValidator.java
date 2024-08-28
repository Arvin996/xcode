package cn.xk.xcode.validation;

import cn.hutool.core.text.CharSequenceUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Administrator
 * @Date 2024/8/27 14:14
 * @Version V1.0.0
 * @Description EmailValidator
 */
public class EmailValidator implements ConstraintValidator<Email, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // 如果手机号为空，默认不校验，即校验通过
        if (CharSequenceUtil.isEmpty(value)) {
            return true;
        }
        // 校验邮箱
        Pattern p = Pattern.compile("[a-zA-Z0-9]+@[A-Za-z0-9]+\\.[a-z0-9]");
        Matcher m = p.matcher(value);
        return m.find();
    }
}
