package cn.xk.xcode.handler.message.impl;

import cn.xk.xcode.core.annotation.Handler;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.enums.ChannelTypeEnum;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.handler.message.AbstractHandler;
import cn.xk.xcode.handler.message.SingeSendMessageResult;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.time.LocalDateTime;
import java.util.*;

import static cn.xk.xcode.config.GlobalMessageConstants.CHANNEL_EMAIL_PROPERTY_NOT_CONFIG;

/**
 * @Author xuk
 * @Date 2025/3/13 17:00
 * @Version 1.0.0
 * @Description EmailMessageHandler
 **/
@Slf4j
@Handler
public class EmailMessageHandler extends AbstractHandler {

    private JavaMailSenderImpl javaMailSender;

    @Override
    public void validateChannelAccountParamValue(Map<String, Object> channelAccountParamsValueMap) {
        if (!channelAccountParamsValueMap.containsKey("username")) {
            ExceptionUtil.castServiceException(CHANNEL_EMAIL_PROPERTY_NOT_CONFIG, "username");
        }
        if (!channelAccountParamsValueMap.containsKey("host")) {
            ExceptionUtil.castServiceException(CHANNEL_EMAIL_PROPERTY_NOT_CONFIG, "host");
        }
        if (!channelAccountParamsValueMap.containsKey("password")) {
            ExceptionUtil.castServiceException(CHANNEL_EMAIL_PROPERTY_NOT_CONFIG, "password");
        }
        if (!channelAccountParamsValueMap.containsKey("port")) {
            ExceptionUtil.castServiceException(CHANNEL_EMAIL_PROPERTY_NOT_CONFIG, "port");
        }
    }

    @Override
    protected void initChannelSendClient() {
        javaMailSender = getJavaMailSender();
    }

    @Override
    protected SingeSendMessageResult doSendMessage(String receiver, MessageTask messageTask, MessageChannelAccountPo messageChannelAccountPo) {
        SingeSendMessageResult singeSendMessageResult = new SingeSendMessageResult();
        singeSendMessageResult.setReceiver(receiver);
        singeSendMessageResult.setExecTime(LocalDateTime.now());
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("发卡平台系统管理员" + '<' + javaMailSender.getUsername() + '>');
        message.setTo(receiver);
        String realMessageCount = messageTask.getMessageContent();
        if (!JSON.isValid(realMessageCount)) {
            singeSendMessageResult.setSuccess(false);
            singeSendMessageResult.setFailMsg("消息模板格式不正确，非json格式:" + realMessageCount);
            return singeSendMessageResult;
        }
        JSONObject jsonObject = JSONObject.parseObject(realMessageCount);
        if (!jsonObject.containsKey("subject")) {
            singeSendMessageResult.setSuccess(false);
            singeSendMessageResult.setFailMsg("消息模板格式不正确，缺少subject字段" );
            return singeSendMessageResult;
        }
        if (!jsonObject.containsKey("content")) {
            singeSendMessageResult.setSuccess(false);
            singeSendMessageResult.setFailMsg("消息模板格式不正确，缺少content字段");
            return singeSendMessageResult;
        }
        message.setSubject(jsonObject.getString("subject"));
        message.setText(jsonObject.getString("content"));
        try {
            javaMailSender.send(message);
            singeSendMessageResult.setSuccess(true);
            singeSendMessageResult.setSuccessTime(LocalDateTime.now());
        } catch (Exception e) {
            log.error("发送邮件失败:{}", e.getMessage());
            singeSendMessageResult.setSuccess(false);
            singeSendMessageResult.setFailMsg(e.getMessage());
        }
        return singeSendMessageResult;
    }


    @Override
    public String channelCode() {
        return ChannelTypeEnum.EMAIL.getCode();
    }

    @Override
    public boolean needRateLimit() {
        return false;
    }


    private JavaMailSenderImpl getJavaMailSender() {
        Map<String, Object> channelAccountParamsValue = getChannelAccountParamsValue();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setUsername(channelAccountParamsValue.get("username").toString());
        mailSender.setHost(channelAccountParamsValue.get("host").toString());
        mailSender.setPort(Integer.parseInt(channelAccountParamsValue.get("port").toString()));
        mailSender.setPassword(channelAccountParamsValue.get("password").toString());
        mailSender.setProtocol("smtp");
        mailSender.setDefaultEncoding("UTF-8");
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }
}
