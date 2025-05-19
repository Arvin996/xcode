package cn.xk.xcode.handler.corn;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.annotation.Handler;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.po.MessageChannelPo;
import cn.xk.xcode.entity.po.MessageTaskDetailPo;
import cn.xk.xcode.entity.po.MessageTaskPo;
import cn.xk.xcode.enums.MessageTaskStatusEnum;
import cn.xk.xcode.handler.MessageHandlerHolder;
import cn.xk.xcode.handler.message.HandlerResult;
import cn.xk.xcode.handler.message.IHandler;
import cn.xk.xcode.service.MessageChannelAccountService;
import cn.xk.xcode.service.MessageChannelService;
import cn.xk.xcode.service.MessageTaskDetailService;
import cn.xk.xcode.service.MessageTaskService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @Author xuk
 * @Date 2025/3/10 17:17
 * @Version 1.0.0
 * @Description CsvReceiversCronTaskHandler
 **/
@Handler
@Slf4j
public class CornReceiversTaskHandler {

    @Resource
    private MessageTaskService messageTaskService;

    @Resource
    private MessageHandlerHolder messageHandlerHolder;

    @Resource
    private MessageChannelService messageChannelService;

    @Resource
    private MessageChannelAccountService messageChannelAccountService;

    @Resource
    private MessageTaskDetailService messageTaskDetailService;

    public void handle(Integer taskId) {
        MessageTaskPo messageTaskPo = messageTaskService.getById(taskId);
        if (Objects.isNull(messageTaskPo)) {
            log.error("CsvReceiversCronTaskHandler#handle messageTaskPo is null");
            return;
        }
        if (StrUtil.isBlank(messageTaskPo.getReceivers())) {
            log.error("CsvReceiversCronTaskHandler#handle receivers is null");
            return;
        }
        MessageTask messageTask = BeanUtil.toBean(messageTaskPo, MessageTask.class);
        MessageChannelPo messageChannelPo = messageChannelService.getById(messageTaskPo.getChannelId());
        if (ObjectUtil.isNull(messageChannelPo)) {
            log.error("CsvReceiversCronTaskHandler#handle messageChannelPo is null");
            return;
        }
        messageTask.setMsgChannel(messageChannelPo.getCode());
        MessageChannelAccountPo messageChannelAccountPo = messageChannelAccountService.getById(messageTaskPo.getAccountId());
        IHandler handler = messageHandlerHolder.routeHandler(messageTask.getMsgChannel());
        HandlerResult result = null;
        try {
            result = handler.doSendMessage(messageTask, messageChannelAccountPo);
        } catch (InterruptedException e) {
            log.error("CsvReceiversCronTaskHandler#handle InterruptedException:{}", e.getMessage());
        }
        if (Objects.isNull(result)) {
            return;
        }
        List<MessageTaskDetailPo> list = result.getList();
        Integer successCount = result.getSuccessCount();
        if (successCount == messageTask.getReceiverSet().size() && successCount != 0) {
            messageTaskPo.setStatus(MessageTaskStatusEnum.ALL_SUCCESS.getStatus());
        } else if (successCount == 0) {
            messageTaskPo.setStatus(MessageTaskStatusEnum.FAIL.getStatus());
        } else {
            messageTaskPo.setStatus(MessageTaskStatusEnum.PART_SUCCESS.getStatus());
        }
        messageTaskService.updateById(messageTaskPo);
        // 失败重试
        if (CollectionUtil.isNotEmpty(list)) {
            messageTaskDetailService.saveBatch(list);
        }
    }
}
