package cn.xk.xcode.service.sms;

import cn.hutool.core.util.IdUtil;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.discard.account.sms.TencentSmsChannelAccount;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.po.MessageTaskDetailPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.handler.message.HandlerResult;
import cn.xk.xcode.service.MessageChannelAccountService;
import com.alibaba.fastjson2.JSON;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20210111.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static cn.xk.xcode.config.GlobalMessageConstants.MESSAGE_ACCOUNT_IS_DISABLED;

/**
 * @Author xuk
 * @Date 2025/3/13 14:29
 * @Version 1.0.0
 * @Description TengXunCloudSmsService
 **/
@Service
@Slf4j
public class TengXunCloudSmsService implements ISmsService {

    @Resource
    private MessageChannelAccountService messageChannelAccountService;


    @Override
    public HandlerResult sendMessage(SmsReqDto smsReqDto) {
        MessageChannelAccountPo channelAccountPo = messageChannelAccountService.getById(smsReqDto.getAccountId());
        if (CommonStatusEnum.isDisable(channelAccountPo.getStatus())) {
            ExceptionUtil.castServiceException(MESSAGE_ACCOUNT_IS_DISABLED);
        }
        TencentSmsChannelAccount tencentSmsChannelAccount = JSON.parseObject(channelAccountPo.getChannelConfig(), TencentSmsChannelAccount.class);
        SmsClient client = init(tencentSmsChannelAccount);
        SendSmsRequest req = new SendSmsRequest();
        String[] phoneNumberSet1 = smsReqDto.getPhones().toArray(new String[smsReqDto.getPhones().size() - 1]);
        req.setPhoneNumberSet(phoneNumberSet1);
        req.setSmsSdkAppId(tencentSmsChannelAccount.getSmsSdkAppId());
        req.setSignName(tencentSmsChannelAccount.getSignName());
        req.setTemplateId(tencentSmsChannelAccount.getTemplateId());
        String[] templateParamSet1 = {smsReqDto.getContent()};
        req.setTemplateParamSet(templateParamSet1);
        req.setSessionContext(IdUtil.fastSimpleUUID());
        try {
            Integer successCount = 0;
            List<MessageTaskDetailPo> messageTaskDetailPoList = new ArrayList<>();
            SendSmsResponse response = client.SendSms(req);
            for (SendStatus sendStatus : response.getSendStatusSet()) {
                boolean isSuccess = "Ok".equals(sendStatus.getCode());
                if (isSuccess) {
                    successCount++;
                }
                MessageTaskDetailPo messageTaskDetailPo = new MessageTaskDetailPo();
                messageTaskDetailPo.setTaskId(smsReqDto.getTaskId());
                messageTaskDetailPo.setReceiver(sendStatus.getPhoneNumber());
                messageTaskDetailPo.setExecTime(LocalDateTime.now());
                messageTaskDetailPo.setStatus(isSuccess ? "0" : "1");
                messageTaskDetailPo.setRetryTimes(0);
                messageTaskDetailPo.setFailMsg(isSuccess ? null : sendStatus.getMessage());
                messageTaskDetailPo.setSuccessTime(isSuccess ? LocalDateTime.now() : null);
                messageTaskDetailPoList.add(messageTaskDetailPo);
            }
            return HandlerResult.builder().successCount(successCount).list(messageTaskDetailPoList).build();
        } catch (TencentCloudSDKException e) {
            log.error("TengXunCloudSmsService#sendMessage fail:{}", e.getMessage());
            return null;
        }
    }

    private SmsClient init(TencentSmsChannelAccount account) {
        Credential cred = new Credential(account.getSecretId(), account.getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(TencentSmsChannelAccount.url);
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        return new SmsClient(cred, account.getRegion(), clientProfile);
    }

    @Override
    public SmsPlatEnum type() {
        return SmsPlatEnum.TENG_XUN_CLOUD;
    }
}
