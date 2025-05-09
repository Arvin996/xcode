package cn.xk.xcode.handler.message.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.xk.xcode.core.annotation.Handler;
import cn.xk.xcode.entity.account.other.EmailAccount;
import cn.xk.xcode.entity.content.EmailContentModel;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.po.MessageTaskDetailPo;
import cn.xk.xcode.entity.task.MessageTask;
import cn.xk.xcode.enums.ChannelTypeEnum;
import cn.xk.xcode.handler.message.AbstractHandler;
import cn.xk.xcode.handler.message.HandlerResult;
import com.alibaba.fastjson2.JSON;
import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Author xuk
 * @Date 2025/3/13 17:00
 * @Version 1.0.0
 * @Description EmailMessageHandler
 **/
@Slf4j
@Handler
public class EmailMessageHandler extends AbstractHandler {

    @Override
    public HandlerResult doSendMessage(MessageTask messageTask, MessageChannelAccountPo messageChannelAccountPo) {
        EmailAccount emailAccount = JSON.parseObject(messageChannelAccountPo.getChannelConfig(), EmailAccount.class);
        EmailContentModel emailContentModel = JSON.parseObject(messageTask.getMessageContent(), EmailContentModel.class);
        MailAccount mailAccount = new MailAccount();
        mailAccount.setHost(emailAccount.getHost());
        mailAccount.setPort(emailAccount.getPort());
        mailAccount.setUser(emailAccount.getUsername());
        mailAccount.setPass(emailAccount.getPassword());
        mailAccount.setFrom(emailAccount.getUsername());
        List<MessageTaskDetailPo> list = new ArrayList<>();
        Integer successCount = 0;
        if (CollectionUtils.isNotEmpty(messageTask.getReceiverSet())) {
            Set<String> receiverSet = messageTask.getReceiverSet();
            for (String receiver : receiverSet) {
                try {
                    File file;
                    if (!StrUtil.isEmpty(emailContentModel.getUrl())) {
                        file = new File(emailContentModel.getUrl());
                        MailUtil.send(mailAccount, receiver, emailContentModel.getTitle(), emailContentModel.getContent(), true, file);
                    } else {
                        MailUtil.send(mailAccount, receiver, emailContentModel.getTitle(), emailContentModel.getContent(), true);
                    }
                    MessageTaskDetailPo messageTaskDetailPo = new MessageTaskDetailPo();
                    messageTaskDetailPo.setTaskId(messageTask.getId());
                    messageTaskDetailPo.setReceiver(receiver);
                    messageTaskDetailPo.setExecTime(LocalDateTime.now());
                    messageTaskDetailPo.setStatus("0");
                    messageTaskDetailPo.setRetryTimes(0);
                    messageTaskDetailPo.setFailMsg(null);
                    messageTaskDetailPo.setSuccessTime(LocalDateTime.now());
                    list.add(messageTaskDetailPo);
                    successCount++;
                } catch (Exception e) {
                    log.error("EmailMessageHandler#doSendMessage fail!{}", e);
                    MessageTaskDetailPo messageTaskDetailPo = new MessageTaskDetailPo();
                    messageTaskDetailPo.setTaskId(messageTask.getId());
                    messageTaskDetailPo.setReceiver(receiver);
                    messageTaskDetailPo.setExecTime(LocalDateTime.now());
                    messageTaskDetailPo.setStatus("1");
                    messageTaskDetailPo.setRetryTimes(0);
                    messageTaskDetailPo.setFailMsg(e.getMessage());
                    messageTaskDetailPo.setSuccessTime(null);
                    list.add(messageTaskDetailPo);
                }
            }
        }
        return HandlerResult.builder().successCount(successCount).list(list).build();
    }

    @Override
    public String channelCode() {
        return ChannelTypeEnum.EMAIL.getCode();
    }

    @Override
    public boolean needRateLimit() {
        return true;
    }
}
