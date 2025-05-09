package cn.xk.xcode.handler.message;

import cn.xk.xcode.entity.task.MessageTask;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/11 15:31
 * @Version 1.0.0
 * @Description IHandler
 **/
public interface IHandler {

    void sendMessage(MessageTask messageTask);

    void withDrawMessage(List<Integer> messageTaskDetailIds);

    List<WithdrawResult> doWithDrawMessage(List<Integer> messageTaskDetailIds);

    String channelCode();

    boolean needRateLimit();
}
