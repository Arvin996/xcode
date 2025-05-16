package cn.xk.xcode.service.task;

import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.handler.MessageHandlerHolder;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/4/17 13:58
 * @Version 1.0.0
 * @Description SendMessageTask
 **/
@Component
@Scope("prototype")
public class SendMessageTask extends Thread {

    @Resource
    private MessageHandlerHolder messageHandlerHolder;

    @Setter
    private MessageTask messageTask;

    @Override
    public void run() {
        messageHandlerHolder.routeHandler(messageTask.getSendType()).sendMessage(messageTask);
    }
}
