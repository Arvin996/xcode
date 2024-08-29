package cn.xk.xcode.handler.core;

import cn.xk.xcode.config.CheckCodeProperties;
import cn.xk.xcode.entity.dto.CheckCodeGenReqDto;
import cn.xk.xcode.entity.vo.CheckCodeGenResultVo;
import cn.xk.xcode.enums.CheckCodeGenerateType;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.CheckCodeHandlerStrategy;
import cn.xk.xcode.handler.SaveCheckCodeCacheStrategy;
import cn.xk.xcode.utils.CheckCodeGenUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.CHECK_CODE_SEND_ERROR;

/**
 * @Author xuk
 * @Date 2024/7/31 21:10
 * @Version 1.0
 * @Description PhoneCheckCodeHandler
 */
public class MobileCheckCodeHandler extends CheckCodeHandlerStrategy {

    private final DefaultProfile defaultProfile;
    private final String signName;
    private final Integer expiredTime;

    public MobileCheckCodeHandler(SaveCheckCodeCacheStrategy saveCheckCodeStrategy
            , CheckCodeProperties checkCodeProperties
            , DefaultProfile defaultProfile
            , String signName
            , Integer expiredTime) {
        super(saveCheckCodeStrategy, checkCodeProperties);
        this.defaultProfile = defaultProfile;
        this.expiredTime = expiredTime;
        this.signName = signName;
    }

    @Override
    public CheckCodeGenResultVo generate(CheckCodeGenReqDto checkCodeGenReqDto, int len) {
        String code = sendMessage(checkCodeGenReqDto, len);
        return CheckCodeGenResultVo.builder().code(code).picCode(null).build();
    }

    @Override
    public String sendMessage(CheckCodeGenReqDto checkCodeGenReqDto, int len) {
        String code = CheckCodeGenUtils.genCode(len);
        String phone = checkCodeGenReqDto.getMobile();
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
    public CheckCodeGenerateType getType() {
        return CheckCodeGenerateType.MOBILE;
    }
}
