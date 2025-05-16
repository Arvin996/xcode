package cn.xk.xcode.service.consume;

import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.core.ThreadPoolExecutorHolder;
import cn.xk.xcode.entity.po.MessageTaskDetailPo;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.handler.MessageHandlerHolder;
import cn.xk.xcode.service.MessageTaskDetailService;
import cn.xk.xcode.service.MessageTaskService;
import cn.xk.xcode.service.task.SendMessageTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @Author xuk
 * @Date 2025/3/14 11:40
 * @Version 1.0.0
 * @Description ConsumeMessageService
 **/
@Service
public class ConsumeMessageService {

    @Resource
    private ThreadPoolExecutorHolder threadPoolExecutorHolder;

    @Resource
    private MessageHandlerHolder messageHandlerHolder;

    @Resource
    private MessageTaskDetailService messageTaskDetailService;

    @Resource
    private MessageTaskService messageTaskService;

    public void consumeSendMessage(MessageTask messageTask) {
        SendMessageTask sendMessageTask = SpringUtil.getBean(SendMessageTask.class);
        sendMessageTask.setMessageTask(messageTask);
        threadPoolExecutorHolder.routeThreadPool(messageTask.getSendType())
                .execute(sendMessageTask);
    }

    public void consumeWithDrawMessage(Integer taskDetailId) {
        MessageTaskDetailPo messageTaskDetailPo = messageTaskDetailService.getById(taskDetailId);
        String code = messageTaskService.getById(messageTaskDetailPo.getTaskId()).getSendType();
        messageHandlerHolder.routeHandler(code).withDrawMessage(Collections.singletonList(taskDetailId));
    }
}
