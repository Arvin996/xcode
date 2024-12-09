package cn.xk.xcode.handler.core;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.xk.xcode.config.CaptchaProperties;
import cn.xk.xcode.entity.GenerateCodeResEntity;
import cn.xk.xcode.entity.dto.CaptchaGenReqDto;
import cn.xk.xcode.entity.dto.CaptchaVerifyReqDto;
import cn.xk.xcode.enums.CaptchaGenerateType;
import cn.xk.xcode.enums.PicCaptchaType;
import cn.xk.xcode.handler.CaptchaHandlerStrategy;
import cn.xk.xcode.handler.SaveCaptchaCacheStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 * @Author xuk
 * @Date 2024/7/31 21:11
 * @Version 1.0
 * @Description PicCaptchaHandler
 */
@Slf4j
public class PicCaptchaHandler extends CaptchaHandlerStrategy {

    private final AbstractCaptcha abstractCaptcha;
    private static final ExpressionParser parser = new SpelExpressionParser();

    public PicCaptchaHandler(SaveCaptchaCacheStrategy saveCaptchaStrategy, CaptchaProperties captchaProperties, AbstractCaptcha abstractCaptcha) {
        super(saveCaptchaStrategy, captchaProperties);
        CaptchaProperties.CaptchaPicProperties captchaPropertiesPic = captchaProperties.getPic();
        PicCaptchaType picCaptchaType = captchaPropertiesPic.getType();
        this.abstractCaptcha = abstractCaptcha;
        if (picCaptchaType.equals(PicCaptchaType.CHAR)) {
            codeGenerator = new RandomGenerator(RandomUtil.BASE_CHAR_NUMBER, captchaProperties.getPic().getCharLength());
        } else {
            codeGenerator = new MathGenerator(captchaProperties.getPic().getMathLength());
        }
        abstractCaptcha.setGenerator(codeGenerator);
    }

    @Override
    public GenerateCodeResEntity generate(CaptchaGenReqDto captchaGenReqDto) {
        abstractCaptcha.createCode();
        // 如果是数学验证码，使用SpEL表达式处理验证码结果
        String code = abstractCaptcha.getCode();
        if (this.captchaProperties.getPic().getType().equals(PicCaptchaType.MATH)) {
            Expression exp = parser.parseExpression(StringUtils.remove(code, "="));
            code = exp.getValue(String.class);
        }
        String uuid = IdUtil.simpleUUID();
        return GenerateCodeResEntity.builder().uuid(uuid).code(code).picCode(abstractCaptcha.getImageBase64()).build();
    }

    @Override
    public void doCodeSave(CaptchaGenReqDto captchaGenReqDto, GenerateCodeResEntity generateCodeResEntity) {
        saveCaptchaStrategy.save(generateCodeResEntity.getUuid() + generateCodeResEntity.getCode(),  generateCodeResEntity.getUuid() + generateCodeResEntity.getCode());
    }

    @Override
    public String getLocalCodeKey(CaptchaVerifyReqDto captchaVerifyReqDto) {
        return captchaVerifyReqDto.getCode();
    }

    @Override
    public String sendMessage(CaptchaGenReqDto captchaGenReqDto) {
        return "";
    }

    @Override
    public CaptchaGenerateType getType() {
        return CaptchaGenerateType.PIC;
    }
}
