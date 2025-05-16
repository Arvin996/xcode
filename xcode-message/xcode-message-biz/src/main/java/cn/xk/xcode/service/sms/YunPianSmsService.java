package cn.xk.xcode.service.sms;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.discard.account.sms.YunPianSmsChannelAccount;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.po.MessageTaskDetailPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.handler.message.HandlerResult;
import cn.xk.xcode.service.MessageChannelAccountService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

import static cn.xk.xcode.config.GlobalMessageConstants.MESSAGE_ACCOUNT_IS_DISABLED;
import static cn.xk.xcode.config.GlobalMessageConstants.SMS_SEND_FAILED;

/**
 * @Author xuk
 * @Date 2025/3/13 14:27
 * @Version 1.0.0
 * @Description YunPianSmsService
 **/
@Service
public class YunPianSmsService implements ISmsService {

    @Resource
    private MessageChannelAccountService messageChannelAccountService;

    @Override
    public HandlerResult sendMessage(SmsReqDto smsReqDto) {
        Integer accountId = smsReqDto.getAccountId();
        MessageChannelAccountPo channelAccountPo = messageChannelAccountService.getById(accountId);
        if (CommonStatusEnum.isDisable(channelAccountPo.getStatus())) {
            ExceptionUtil.castServiceException(MESSAGE_ACCOUNT_IS_DISABLED);
        }
        YunPianSmsChannelAccount yunPianSmsChannelAccount = JSON.parseObject(channelAccountPo.getChannelConfig(), YunPianSmsChannelAccount.class);
        Map<String, Object> params = new HashMap<>();
        params.put("apikey", yunPianSmsChannelAccount.getApikey());
        params.put("mobile", String.join(",", smsReqDto.getPhones()));
        params.put("text", smsReqDto.getContent());
        HttpResponse httpResponse = HttpRequest.post(YunPianSmsChannelAccount.URL)
                .header(Header.CONTENT_TYPE.getValue(), "application/x-www-form-urlencoded;charset=utf-8")
                .header(Header.ACCEPT.getValue(), "application/json")
                .form(params)
                .execute();
        if (!httpResponse.isOk()) {
            ExceptionUtil.castServiceException(SMS_SEND_FAILED);
        }
        String body = httpResponse.body();
        JSONObject jsonObject = JSON.parseObject(body);
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        List<MessageTaskDetailPo> list = new ArrayList<>();
        Integer successCount = 0;
        for (Object object : jsonArray) {
            JSONObject result = JSON.parseObject(JSON.toJSONString(object));
            boolean isSuccess = result.getIntValue("code") == 0;
            if (isSuccess) {
                successCount++;
            }
            MessageTaskDetailPo messageTaskDetailPo = new MessageTaskDetailPo();
            messageTaskDetailPo.setTaskId(smsReqDto.getTaskId());
            messageTaskDetailPo.setReceiver(result.getString("mobile"));
            messageTaskDetailPo.setExecTime(LocalDateTime.now());
            messageTaskDetailPo.setStatus(isSuccess ? "0" : "1");
            messageTaskDetailPo.setRetryTimes(0);
            messageTaskDetailPo.setFailMsg(isSuccess ? null : result.getString("msg"));
            messageTaskDetailPo.setSuccessTime(isSuccess ? LocalDateTime.now() : null);

        }
        return HandlerResult.builder().successCount(successCount).list(list).build();
    }

    @Override
    public SmsPlatEnum type() {
        return SmsPlatEnum.YUNPIAN;
    }
}
