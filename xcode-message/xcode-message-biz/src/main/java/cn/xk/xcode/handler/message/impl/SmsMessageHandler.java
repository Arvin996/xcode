package cn.xk.xcode.handler.message.impl;

import cn.xk.xcode.core.annotation.Handler;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.enums.ChannelTypeEnum;
import cn.xk.xcode.handler.message.AbstractHandler;
import cn.xk.xcode.handler.message.HandlerResult;
import cn.xk.xcode.service.sms.ISmsService;
import cn.xk.xcode.service.sms.SmsPlatEnum;
import cn.xk.xcode.service.sms.SmsReqDto;
import cn.xk.xcode.service.sms.SmsServiceHolder;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/3/13 16:29
 * @Version 1.0.0
 * @Description SmsMessageHandler
 **/
@Handler
public class SmsMessageHandler extends AbstractHandler {

    @Resource
    private SmsServiceHolder smsServiceHolder;

    @Override
    public HandlerResult doSendMessage(MessageTask messageTask, MessageChannelAccountPo messageChannelAccountPo) {
        ISmsService smsService = smsServiceHolder.getSmsService(SmsPlatEnum.getByName(messageChannelAccountPo.getAccountName()));
        return smsService.sendMessage(SmsReqDto
                .builder()
                .accountId(messageChannelAccountPo.getId())
                .phones(messageTask.getReceiverSet())
                .content(messageTask.getMessageContent())
                .taskId(messageTask.getId())
                .build());
    }

    @Override
    public String channelCode() {
        return ChannelTypeEnum.SMS.getCode();
    }

    @Override
    public boolean needRateLimit() {
        return true;
    }
}
