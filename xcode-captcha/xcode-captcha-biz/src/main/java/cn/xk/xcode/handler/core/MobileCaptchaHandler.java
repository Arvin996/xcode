package cn.xk.xcode.handler.core;

import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.config.CaptchaProperties;
import cn.xk.xcode.entity.GenerateCodeResEntity;
import cn.xk.xcode.entity.dto.CaptchaGenReqDto;
import cn.xk.xcode.entity.dto.CaptchaVerifyReqDto;
import cn.xk.xcode.enums.CaptchaGenerateType;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.CaptchaHandlerStrategy;
import cn.xk.xcode.handler.SaveCaptchaCacheStrategy;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

import static cn.xk.xcode.enums.CaptchaGlobalErrorCodeConstants.CAPTCHA_REPEAT_SEND;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.*;

/**
 * @Author xuk
 * @Date 2024/7/31 21:10
 * @Version 1.0
 * @Description PhoneCaptchaHandler
 */
public class MobileCaptchaHandler extends CaptchaHandlerStrategy {

    private final DefaultProfile defaultProfile;
    private final String signName;
    private final Integer expiredTime;

    public MobileCaptchaHandler(SaveCaptchaCacheStrategy saveCaptchaStrategy
            , CaptchaProperties captchaProperties
            , DefaultProfile defaultProfile
            , String signName
            , Integer expiredTime) {
        super(saveCaptchaStrategy, captchaProperties);
        this.defaultProfile = defaultProfile;
        this.expiredTime = expiredTime;
        this.signName = signName;
        this.codeGenerator = new RandomGenerator(RandomUtil.BASE_NUMBER, captchaProperties.getMobile().getLength());
    }

    @Override
    public GenerateCodeResEntity generate(CaptchaGenReqDto captchaGenReqDto) {
        if (StrUtil.isNotEmpty(this.saveCaptchaStrategy.get(captchaGenReqDto.getMobile()))) {
            throw new ServiceException(CAPTCHA_REPEAT_SEND);
        }
        String code = sendMessage(captchaGenReqDto);
        return GenerateCodeResEntity.builder().code(code).picCode(null).build();
    }

    @Override
    public void doCodeSave(CaptchaGenReqDto captchaGenReqDto, GenerateCodeResEntity generateCodeResEntity) {
        saveCaptchaStrategy.save(captchaGenReqDto.getMobile(), generateCodeResEntity.getCode());
    }

    @Override
    public String getLocalCodeKey(CaptchaVerifyReqDto captchaVerifyReqDto) {
        return captchaVerifyReqDto.getMobile();
    }


    @Override
    public String sendMessage(CaptchaGenReqDto captchaGenReqDto) {
        String code = this.codeGenerator.generate();
        String phone = captchaGenReqDto.getMobile();
        IAcsClient client = new DefaultAcsClient(defaultProfile);
        SendSmsRequest request = new SendSmsRequest();
        request.setSysRegionId(defaultProfile.getRegionId());
        request.setPhoneNumbers(phone);
        request.setSignName(signName);
        request.setTemplateCode(("验证码为{code}, 请在{expiredTime}秒内完成验证" + "\n"
                + "【xx应用】"));
        request.setTemplateParam("{code: " + code + "," + "expiredTime :" + expiredTime + "}");
        try {
            client.getAcsResponse(request);
        } catch (ClientException e) {
            throw new ServiceException(CHECK_CODE_SEND_ERROR);
        }
        return code;
    }

    @Override
    public CaptchaGenerateType getType() {
        return CaptchaGenerateType.MOBILE;
    }
}
