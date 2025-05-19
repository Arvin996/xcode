package cn.xk.xcode.handler.message.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.xk.xcode.constants.MessageChannelUrlConstant;
import cn.xk.xcode.core.annotation.Handler;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.enums.MessageChannelEnum;
import cn.xk.xcode.handler.message.AbstractHandler;
import cn.xk.xcode.handler.message.SingeSendMessageResult;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/5/19 8:18
 * @Version 1.0.0
 * @Description FeiShuRobotWebHookMessageHandler
 **/
@Handler
public class FeiShuRobotWebHookMessageHandler extends AbstractHandler {

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    @Override
    public void validateChannelAccountParamValue(Map<String, Object> channelAccountParamsValueMap) {

    }

    @Override
    protected void initChannelSendClient() {

    }

    @Override
    protected SingeSendMessageResult doSendMessage(String receiver, MessageTask messageTask, MessageChannelAccountPo messageChannelAccountPo) {
        Map<String, Object> extraParams = messageTask.getExtraParams();
        SingeSendMessageResult singeSendMessageResult = new SingeSendMessageResult();
        singeSendMessageResult.setReceiver(receiver);
        singeSendMessageResult.setExecTime(LocalDateTime.now());
        if (!extraParams.containsKey("token")) {
            singeSendMessageResult.setSuccess(false);
            singeSendMessageResult.setFailMsg("飞书机器人token不存在");
            return singeSendMessageResult;
        }
        String token = extraParams.get("token").toString();
        String secret = "";
        if (extraParams.containsKey("secret")) {
            secret = extraParams.get("secret").toString();
        }
        Map<String, Object> content = new HashMap<>();
        Map<String, Object> text = new HashMap<>();
        content.put("msg_type", "text");
        text.put("text", messageTask.getMessageContent());
        content.put("content", text);
        //发送post请求
        String url = String.format(MessageChannelUrlConstant.FEI_SHU_ROBOT_WEBHOOK_URL, token);
        int timestamp = (int) (System.currentTimeMillis() / 1000);
        if (StrUtil.isNotEmpty(secret)) {
            content.put("timestamp", timestamp);
            try {
                String signature = generateSignature(secret, timestamp);
                content.put("sign", signature);
            } catch (Exception e) {
                singeSendMessageResult.setSuccess(false);
                singeSendMessageResult.setFailMsg("飞书机器人签名错误：" + e.getMessage());
                return singeSendMessageResult;
            }
        }
        HttpResponse httpResponse = HttpRequest.post(url).body(JSON.toJSONString(content)).execute();
        if (!httpResponse.isOk()) {
            singeSendMessageResult.setSuccess(false);
            singeSendMessageResult.setFailMsg("发送消息失败" + httpResponse.body());
            return singeSendMessageResult;
        } else {
            String body = httpResponse.body();
            JSONObject jsonObject = JSON.parseObject(body);
            if (jsonObject.getIntValue("code") == 0) {
                singeSendMessageResult.setSuccess(true);
                singeSendMessageResult.setSuccessTime(LocalDateTime.now());
                return singeSendMessageResult;
            } else {
                singeSendMessageResult.setSuccess(false);
                singeSendMessageResult.setFailMsg(body);
                return singeSendMessageResult;
            }
        }
    }

    @Override
    public String channelCode() {
        return MessageChannelEnum.FEI_SHU_ROBOT.getCode();
    }

    @Override
    public boolean needRateLimit() {
        return false;
    }

    private static String generateSignature(String secret, int timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
        mac.init(new SecretKeySpec(stringToSign.getBytes(StandardCharsets.UTF_8), HMAC_SHA256_ALGORITHM));
        byte[] signData = mac.doFinal(new byte[]{});
        return new String(Base64.encodeBase64(signData));
    }
}
