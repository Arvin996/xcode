package cn.xk.xcode.handler;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.config.CaptchaProperties;
import cn.xk.xcode.entity.GenerateCodeResEntity;
import cn.xk.xcode.entity.dto.CaptchaGenReqDto;
import cn.xk.xcode.entity.dto.CaptchaVerifyReqDto;
import cn.xk.xcode.entity.vo.CaptchaGenResultVo;
import cn.xk.xcode.enums.CaptchaGenerateType;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.util.StringUtils;

import static cn.xk.xcode.enums.CaptchaGlobalErrorCodeConstants.*;

/**
 * @Author xuk
 * @Date 2024/7/31 18:59
 * @Version 1.0
 * @Description BaseCaptchaGenerate
 */
public abstract class CaptchaHandlerStrategy {

    protected final SaveCaptchaCacheStrategy saveCaptchaStrategy;

    protected final CaptchaProperties captchaProperties;

    protected CodeGenerator codeGenerator;

    public CaptchaHandlerStrategy(SaveCaptchaCacheStrategy saveCaptchaStrategy, CaptchaProperties captchaProperties) {
        this.saveCaptchaStrategy = saveCaptchaStrategy;
        this.captchaProperties = captchaProperties;
    }

    public abstract GenerateCodeResEntity generate(CaptchaGenReqDto captchaGenReqDto);

    public CaptchaGenResultVo doGenerateCode(CaptchaGenReqDto captchaGenReqDto) {
        GenerateCodeResEntity generateCodeResEntity = generate(captchaGenReqDto);
        doCodeSave(captchaGenReqDto, generateCodeResEntity);
        return CaptchaGenResultVo.builder().picCode(generateCodeResEntity.getPicCode()).uuid(generateCodeResEntity.getUuid()).build();
    }

    public abstract void doCodeSave(CaptchaGenReqDto captchaGenReqDto, GenerateCodeResEntity generateCodeResEntity);

    public CommonResult<Boolean> verifyCode(CaptchaVerifyReqDto captchaVerifyReqDto) {
        String type = captchaVerifyReqDto.getType();
        if (CaptchaGenerateType.EMAIL.getCode().equals(type)){
            if (StrUtil.isBlank(captchaVerifyReqDto.getEmail())){
                return CommonResult.error(EMAIL_MUST_NOT_NULL, null);
            }
        }else if (CaptchaGenerateType.MOBILE.getCode().equals(type)){
            if (StrUtil.isBlank(captchaVerifyReqDto.getMobile())){
                return CommonResult.error(MOBILE_MUST_NOT_NULL, null);
            }
        }
        String code = captchaVerifyReqDto.getCode();
        String key = getLocalCodeKey(captchaVerifyReqDto);
        String localCode = saveCaptchaStrategy.get(key);
        if (!StringUtils.hasLength(localCode)) {
            return CommonResult.error(CHECK_CODE_IS_EXPIRED, null);
        }
        if (!StrUtil.equalsIgnoreCase(code, localCode)) {
            return CommonResult.error(CHECK_CODE_IS_ERROR, null);
        }
        saveCaptchaStrategy.remove(key);
        return CommonResult.success(true);
    }

    public abstract String getLocalCodeKey(CaptchaVerifyReqDto captchaVerifyReqDto);

    public abstract String sendMessage(CaptchaGenReqDto captchaGenReqDto);

    public abstract CaptchaGenerateType getType();
}
