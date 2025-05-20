package cn.xk.xcode.handler.core;

import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.config.CaptchaProperties;
import cn.xk.xcode.entity.GenerateCodeResEntity;
import cn.xk.xcode.entity.dto.CaptchaGenReqDto;
import cn.xk.xcode.entity.dto.CaptchaVerifyReqDto;
import cn.xk.xcode.enums.*;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.CaptchaHandlerStrategy;
import cn.xk.xcode.handler.SaveCaptchaCacheStrategy;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.rpc.SendMessageProto;
import cn.xk.xcode.rpc.SendMessageTaskServiceGrpc;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.HashMap;

import static cn.xk.xcode.enums.CaptchaGlobalErrorCodeConstants.CAPTCHA_REPEAT_SEND;
import static cn.xk.xcode.enums.CaptchaGlobalErrorCodeConstants.SEND_CAPTCHA_ERROR;

/**
 * @Author xuk
 * @Date 2024/7/31 21:10
 * @Version 1.0
 * @Description PhoneCaptchaHandler
 */
@Slf4j
public class MobileCaptchaHandler extends CaptchaHandlerStrategy {

    @GrpcClient("xcode-message")
    private SendMessageTaskServiceGrpc.SendMessageTaskServiceBlockingStub sendMessageTaskServiceBlockingStub;

    public MobileCaptchaHandler(SaveCaptchaCacheStrategy saveCaptchaStrategy
            , CaptchaProperties captchaProperties) {
        super(saveCaptchaStrategy, captchaProperties);
        this.codeGenerator = new RandomGenerator(RandomUtil.BASE_NUMBER, captchaProperties.getCodeLength());
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
        SendMessageProto.SendMessageTaskRequest.Builder builder = SendMessageProto.SendMessageTaskRequest.newBuilder();
        builder.setClientAccessToken("aa");
        builder.setAccountName("ss");
        builder.setShieldType(ShieldType.NIGHT_NO_SHIELD.getCode());
        builder.setMsgType(MessageSendType.NOW.getCode());
        builder.setMsgChannel(MessageChannelEnum.ALIYUN_SMS.getCode());
        builder.setMsgContentType(MessageContentType.TEMPLATE.getCode());
        builder.setTemplateId("ccc");
        HashMap<String, String> map = MapUtil.of("code", code);
        builder.setContentValueParams(JSON.toJSONString(map));
        builder.setReceiverType(ReceiverTypeEnum.PLAIN.getCode());
        builder.setReceivers(phone);
        // 发送手机短信
        try {
            SendMessageProto.SendMessageTaskResponse response = sendMessageTaskServiceBlockingStub.sendMessageTask(builder.build());
            if (!CommonResult.isSuccess(response.getCode())) {
                String msg = response.getMsg();
                ExceptionUtil.castServerException0(response.getCode(), msg);
            } else {
                SendMessageProto.SendMessageResponse sendMessageResponse = response.getSendMessageResponse();
                int failCount = sendMessageResponse.getFailCount();
                if (failCount > 0) {
                    log.error("发送邮件失败:{}", sendMessageResponse.getFailMessageDetailList(0));
                    ExceptionUtil.castServiceException(SEND_CAPTCHA_ERROR);
                }
            }
        } catch (Exception e) {
            log.error("发送邮件失败:{}", e.getMessage());
            ExceptionUtil.castServiceException(SEND_CAPTCHA_ERROR);
        }
        return code;
    }

    @Override
    public CaptchaGenerateType getType() {
        return CaptchaGenerateType.MOBILE;
    }
}
