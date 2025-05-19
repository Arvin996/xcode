package cn.xk.xcode.handler.message.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.xk.xcode.constants.MessageChannelUrlConstant;
import cn.xk.xcode.core.annotation.Handler;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.enums.MessageChannelEnum;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.handler.message.AbstractHandler;
import cn.xk.xcode.handler.message.SingeSendMessageResult;
import cn.xk.xcode.handler.message.request.SendWxOfficeProgramMessageParams;
import cn.xk.xcode.utils.AccessTokenUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static cn.xk.xcode.config.GlobalMessageConstants.CHANNEL_WX_OFFICE_PROPERTY_NOT_CONFIG;
import static cn.xk.xcode.utils.AccessTokenUtil.WX_OFFICE_TOKEN_PREFIX;

/**
 * @author xukai
 * @version 1.0
 * @date 2025/5/18 18:10
 * @description WxOfficeMessageHandler
 */
@Handler
public class WxOfficeMessageHandler extends AbstractHandler {

    @Override
    public void validateChannelAccountParamValue(Map<String, Object> channelAccountParamsValueMap) {
        if (!channelAccountParamsValueMap.containsKey("appId")) {
            ExceptionUtil.castServiceException(CHANNEL_WX_OFFICE_PROPERTY_NOT_CONFIG, "appId");
        }
        if (!channelAccountParamsValueMap.containsKey("secret")) {
            ExceptionUtil.castServiceException(CHANNEL_WX_OFFICE_PROPERTY_NOT_CONFIG, "secret");
        }
    }

    @Override
    protected void initChannelSendClient() {

    }

    @Override
    protected SingeSendMessageResult doSendMessage(String receiver, MessageTask messageTask, MessageChannelAccountPo messageChannelAccountPo) {
        SingeSendMessageResult singeSendMessageResult = new SingeSendMessageResult();
        singeSendMessageResult.setExecTime(LocalDateTime.now());
        singeSendMessageResult.setReceiver(receiver);
        String token = stringRedisTemplate.opsForValue().get(WX_OFFICE_TOKEN_PREFIX + messageChannelAccountPo.getId());
        if (StrUtil.isEmpty(token)) {
            token = AccessTokenUtil.getWxOfficeAccessToken(messageChannelAccountPo, getChannelAccountParamsValue(), true);
        }
        if (StrUtil.isEmpty(token)) {
            singeSendMessageResult.setSuccess(false);
            singeSendMessageResult.setFailMsg("获取token失败");
        } else {
            SendWxOfficeProgramMessageParams sendWxOfficeProgramMessageParams = new SendWxOfficeProgramMessageParams();
            sendWxOfficeProgramMessageParams.setTemplate_id(messageTask.getTemplateId());
            if (StrUtil.isNotEmpty(messageTask.getPage())){
                Map<String, Object> map = new HashMap<>();
                map.put("appid", getChannelAccountParamsValue().get("appId"));
                map.put("pagepath", messageTask.getPage());
                sendWxOfficeProgramMessageParams.setMiniprogram(map);
            }
            sendWxOfficeProgramMessageParams.setTouser(receiver);
            String contentValueParams = messageTask.getContentValueParams();
            if (!JSON.isValid(contentValueParams)){
                singeSendMessageResult.setSuccess(false);
                singeSendMessageResult.setFailMsg("消息模板格式不正确，非json格式:" + contentValueParams);
            }
            JSONObject jsonObject = JSON.parseObject(contentValueParams);
            Map<String, Map<String, Object>> map = new HashMap<>();
            for (String key : jsonObject.keySet()) {
                map.put(key, jsonObject.getJSONObject(key));
            }
            sendWxOfficeProgramMessageParams.setData(map);
            HttpResponse httpResponse = HttpRequest.post(String.format(MessageChannelUrlConstant.WX_OFFICIAL_ACCOUNT_SEND_URL, token))
                    .body(JSON.toJSONString(sendWxOfficeProgramMessageParams))
                    .execute();
            if (!httpResponse.isOk()){
                singeSendMessageResult.setSuccess(false);
                singeSendMessageResult.setFailMsg(httpResponse.body());
            }else {
                String body = httpResponse.body();
                JSONObject jsonObject1 = JSON.parseObject(body);
                if (jsonObject1.containsKey("errcode") && jsonObject1.getIntValue("errcode") == 0){
                    singeSendMessageResult.setSuccess(true);
                    singeSendMessageResult.setSuccessTime(LocalDateTime.now());
                }else {
                    singeSendMessageResult.setSuccess(false);
                    singeSendMessageResult.setFailMsg(httpResponse.body());
                }
            }
        }
        return singeSendMessageResult;
    }

    @Override
    public String channelCode() {
        return MessageChannelEnum.WX_OFFICE.getCode();
    }

    @Override
    public boolean needRateLimit() {
        return false;
    }
}
