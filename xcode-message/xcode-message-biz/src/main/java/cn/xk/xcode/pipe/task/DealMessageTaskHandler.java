package cn.xk.xcode.pipe.task;

import cn.xk.xcode.core.pipeline.TaskContext;
import cn.xk.xcode.core.pipeline.TaskHandler;
import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.pipe.model.SendMessageTaskModel;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.message.SendMessageServiceHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static cn.xk.xcode.config.GlobalMessageConstants.*;

/**
 * @Author xuk
 * @Date 2025/5/26 12:16
 * @Version 1.0.0
 * @Description DealMessageTaskHandler
 **/
@Component
@Slf4j
public class DealMessageTaskHandler implements TaskHandler<SendMessageTaskModel> {

    @Resource
    private SendMessageServiceHolder sendMessageServiceHolder;

    @Override
    public void handle(TaskContext<SendMessageTaskModel> taskContext) {
        taskContext.setIsBreak(true);
        MessageTask messageTask = taskContext.getTaskModel().getMessageTask();
        CommonResult<?> commonResult = sendMessageServiceHolder.routeSendMessageService(messageTask.getMsgType()).dealMessage(messageTask);
        taskContext.setResult(commonResult);
    }

    @Override
    public int getOrder() {
        return SEND_MESSAGE_TASK_STEP_FOUR;
    }

    @Override
    public String getCode() {
        return SEND_MESSAGE_TASK_CODE;
    }
}
