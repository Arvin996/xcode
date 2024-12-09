package cn.xk.xcode.enums;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2024/12/9 15:05
 * @Version 1.0.0
 * @Description PicCaptchaType
 **/
@Getter
@AllArgsConstructor
public enum PicCaptchaType {

    /**
     * 数字
     */
    MATH(MathGenerator.class),

    /**
     * 字符
     */
    CHAR(RandomGenerator.class);

    private final Class<? extends CodeGenerator> clazz;
}
