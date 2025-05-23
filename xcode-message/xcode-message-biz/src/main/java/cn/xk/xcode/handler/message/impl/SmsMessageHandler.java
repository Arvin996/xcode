package cn.xk.xcode.handler.message.impl;

import cn.xk.xcode.core.annotation.Handler;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.enums.MessageChannelEnum;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.handler.message.AbstractHandler;
import cn.xk.xcode.handler.message.SingeSendMessageResult;
import com.alibaba.fastjson2.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static cn.xk.xcode.config.GlobalMessageConstants.CHANNEL_ALIYUN_SMS_PROPERTY_NOT_CONFIG;

/**
 * @Author xuk
 * @Date 2025/3/13 16:29
 * @Version 1.0.0
 * @Description SmsMessageHandler
 **/
@Handler
@Slf4j
public class SmsMessageHandler extends AbstractHandler {

    private DefaultProfile defaultProfile;

    @Override
    public String channelCode() {
        return MessageChannelEnum.ALIYUN_SMS.getCode();
    }

    @Override
    public boolean needRateLimit() {
        return true;
    }

    @Override
    public void validateChannelAccountParamValue(Map<String, Object> channelAccountParamsValueMap) {
        if (!channelAccountParamsValueMap.containsKey("signName")) {
            ExceptionUtil.castServiceException(CHANNEL_ALIYUN_SMS_PROPERTY_NOT_CONFIG, "signName");
        }
        if (!channelAccountParamsValueMap.containsKey("accessKeyId")) {
            ExceptionUtil.castServiceException(CHANNEL_ALIYUN_SMS_PROPERTY_NOT_CONFIG, "accessKeyId");
        }
        if (!channelAccountParamsValueMap.containsKey("secret")) {
            ExceptionUtil.castServiceException(CHANNEL_ALIYUN_SMS_PROPERTY_NOT_CONFIG, "secret");
        }
        if (!channelAccountParamsValueMap.containsKey("regionId")) {
            ExceptionUtil.castServiceException(CHANNEL_ALIYUN_SMS_PROPERTY_NOT_CONFIG, "regionId");
        }
    }

    @Override
    protected void initChannelSendClient() {
        Map<String, Object> channelAccountParamsValue = getChannelAccountParamsValue();
        defaultProfile = DefaultProfile.getProfile(channelAccountParamsValue.get("regionId").toString(), channelAccountParamsValue.get("accessKeyId").toString(), channelAccountParamsValue.get("secret").toString());
    }

    @Override
    protected SingeSendMessageResult doSendMessage(String receiver, MessageTask messageTask, MessageChannelAccountPo messageChannelAccountPo) {
        SingeSendMessageResult singeSendMessageResult = new SingeSendMessageResult();
        singeSendMessageResult.setExecTime(LocalDateTime.now());
        IAcsClient client = new DefaultAcsClient(defaultProfile);
        SendSmsRequest request = new SendSmsRequest();
        request.setSysRegionId(defaultProfile.getRegionId());
        request.setPhoneNumbers(receiver);
        request.setSignName(getChannelAccountParamsValue().get("signName").toString());
        request.setSysMethod(MethodType.POST);
        request.setTemplateCode(messageTask.getTemplateCode());
        request.setTemplateParam(messageTask.getContentValueParams());
        try {
            singeSendMessageResult.setReceiver(receiver);
            SendSmsResponse sendSmsResponse = client.getAcsResponse(request);
            if ((sendSmsResponse.getCode() != null) && (sendSmsResponse.getCode().equals("OK"))) {
                singeSendMessageResult.setSuccess(true);
                singeSendMessageResult.setSuccessTime(LocalDateTime.now());
            } else {
                singeSendMessageResult.setSuccess(false);
                Map<String, Object> map = new HashMap<>();
                map.put("code", sendSmsResponse.getCode());
                map.put("message", sendSmsResponse.getMessage());
                map.put("requestId", sendSmsResponse.getRequestId());
                map.put("bizId", sendSmsResponse.getBizId());
                singeSendMessageResult.setFailMsg(JSON.toJSONString(map));
            }
        } catch (ClientException e) {
            singeSendMessageResult.setFailMsg(e.getMessage());
            singeSendMessageResult.setSuccess(false);
            log.error("发送短信失败:{}", e.getMessage());
        }
        return singeSendMessageResult;
    }
}
