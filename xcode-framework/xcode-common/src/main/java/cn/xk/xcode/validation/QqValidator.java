package cn.xk.xcode.validation;

import cn.hutool.core.text.CharSequenceUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author xuk
 * @Date 2025/6/4 9:59
 * @Version 1.0.0
 * @Description QqValidator
 **/
public class QqValidator implements ConstraintValidator<Qq, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (CharSequenceUtil.isEmpty(s)) {
            return true;
        }
        // 正则校验qq
        Pattern p = Pattern.compile("^[0-9]{5,11}$");
        Matcher m = p.matcher(s);
        return m.find();
    }
}
