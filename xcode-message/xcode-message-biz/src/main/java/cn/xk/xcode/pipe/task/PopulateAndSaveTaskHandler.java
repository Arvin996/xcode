package cn.xk.xcode.pipe.task;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.pipeline.TaskContext;
import cn.xk.xcode.core.pipeline.TaskHandler;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.entity.po.MessageChannelAccessClientPo;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.po.MessageChannelPo;
import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.enums.MessageChannelEnum;
import cn.xk.xcode.enums.ReceiverTypeEnum;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.pipe.model.SendMessageTaskModel;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.MessageChannelAccessClientService;
import cn.xk.xcode.service.MessageChannelAccountService;
import cn.xk.xcode.service.MessageChannelService;
import cn.xk.xcode.service.MessageTaskService;
import cn.xk.xcode.utils.CsvCountUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static cn.xk.xcode.config.GlobalMessageConstants.*;
import static cn.xk.xcode.entity.def.MessageChannelAccessClientTableDef.MESSAGE_CHANNEL_ACCESS_CLIENT;
import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT;
import static cn.xk.xcode.entity.def.MessageChannelTableDef.MESSAGE_CHANNEL;

/**
 * @Author xuk
 * @Date 2025/5/26 9:29
 * @Version 1.0.0
 * @Description PopulateAndSaveTaskHandler
 **/
@Slf4j
@Component
public class PopulateAndSaveTaskHandler implements TaskHandler<SendMessageTaskModel> {

    @Resource
    private MessageChannelService messageChannelService;

    @Resource
    private MessageChannelAccessClientService messageChannelAccessClientService;

    @Resource
    private MessageChannelAccountService messageChannelAccountService;

    @Resource
    protected MessageTaskService messageTaskService;

    @Override
    public void handle(TaskContext<SendMessageTaskModel> taskContext) {
        SendMessageTaskModel taskModel = taskContext.getTaskModel();
        MessageTask messageTask = taskModel.getMessageTask();
        String msgChannel = messageTask.getMsgChannel();
        MessageChannelPo messageChannelPo = messageChannelService.getOne(MESSAGE_CHANNEL.CODE.eq(msgChannel));
        if (ObjectUtil.isNull(messageChannelPo)) {
            log.error("PopulateAndSaveTaskHandler#handle messageChannelPo is null");
            taskContext.setIsBreak(true);
            taskContext.setResult(CommonResult.error(CHANNEL_NOT_EXISTS, null));
        }
        messageTask.setChannelId(messageChannelPo.getId());
        if (StrUtil.isNotBlank(messageTask.getAccountName())) {
            MessageChannelAccountPo messageChannelAccountPo = messageChannelAccountService.getOne(MESSAGE_CHANNEL_ACCOUNT.ACCOUNT_NAME.eq(messageTask.getAccountName()).and(MESSAGE_CHANNEL_ACCOUNT.CHANNEL_ID.eq(messageChannelPo.getId())));
            if (ObjectUtil.isNull(messageChannelAccountPo)) {
                taskContext.setIsBreak(true);
                taskContext.setResult(CommonResult.error(MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS, null));
            }
            messageTask.setAccountId(messageChannelAccountPo.getId());
        }
        try {
            convertReceivers(messageTask);
            MessageChannelAccessClientPo messageChannelAccessClientPo = messageChannelAccessClientService.getOne(MESSAGE_CHANNEL_ACCESS_CLIENT.ACCESS_TOKEN.eq(messageTask.getClientAccessToken()));
            messageChannelAccessClientService.validateClient(messageChannelAccessClientPo, messageTask.getReceiverSet().size());
            messageTask.setClientId(messageChannelAccessClientPo.getId());
            MessageTaskPo messageTaskPo = BeanUtil.toBean(messageTask, MessageTaskPo.class);
            messageTaskService.save(messageTaskPo);
            messageTask.setId(messageTaskPo.getId());
        } catch (Exception e) {
            taskContext.setIsBreak(true);
            log.error("PopulateAndSaveTaskHandler#handle error:{}", e.getMessage());
            if (e instanceof ServiceException) {
                taskContext.setResult(CommonResult.error((ServiceException) e));
            } else if (e instanceof ServerException) {
                taskContext.setResult(CommonResult.error((ServerException) e));
            } else {
                taskContext.setResult(CommonResult.error(EXEC_MESSAGE_TASK_FAILED, null, getHandlerName()));
            }
        }
    }

    private void convertReceivers(MessageTask messageTask) {
        String msgChannel = messageTask.getMsgChannel();
        if (MessageChannelEnum.FEI_SHU_ROBOT.getCode().equals(msgChannel)) {
            messageTask.setReceiverSet(CollectionUtil.createSingleSet("飞书机器人"));
            messageTask.setReceivers("飞书机器人");
            return;
        }
        if (MessageChannelEnum.DING_DING_ROBOT.getCode().equals(msgChannel)) {
            messageTask.setReceiverSet(CollectionUtil.createSingleSet("钉钉机器人"));
            messageTask.setReceivers("钉钉机器人");
            return;
        }
        String receiverType = messageTask.getReceiverType();
        if (ReceiverTypeEnum.isCsv(receiverType)) {
            Set<String> set = new HashSet<>();
            // todo 对接minio 上传csv文件
            CsvCountUtil.handleCsv(messageTask.getReceivers(), row -> {
                set.add(row.get(0));
            });
            messageTask.setReceiverSet(set);
            messageTask.setReceivers(StrUtil.join(",", set));
        } else {
            List<String> split = StrUtil.splitTrim(messageTask.getReceivers(), ",");
            messageTask.setReceiverSet(CollectionUtil.convertSet(split, Function.identity()));
        }
    }

    @Override
    public int getOrder() {
        return SEND_MESSAGE_TASK_STEP_TWO;
    }

    @Override
    public String getCode() {
        return SEND_MESSAGE_TASK_CODE;
    }
}
