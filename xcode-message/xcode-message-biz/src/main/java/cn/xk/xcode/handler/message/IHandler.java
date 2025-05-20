package cn.xk.xcode.handler.message;

import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.po.MessageTaskDetailPo;
import cn.xk.xcode.handler.message.response.SendMessageResponse;
import cn.xk.xcode.pojo.CommonResult;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/11 15:31
 * @Version 1.0.0
 * @Description IHandler
 **/
public interface IHandler {

    CommonResult<SendMessageResponse> sendMessage(MessageTask messageTask);

    void withDrawMessage(List<Integer> messageTaskDetailIds);

    List<WithdrawResult> doWithDrawMessage(List<Integer> messageTaskDetailIds);

    HandlerResult doSendMessage(MessageTask messageTask, MessageChannelAccountPo messageChannelAccountPo) throws InterruptedException;

    String channelCode();

    boolean needRateLimit();

    CommonResult<SendMessageResponse> reSendTaskMessage(Long taskId);

    CommonResult<SendMessageResponse> reSendSingleTask(Long taskDetailId);

    CommonResult<SendMessageResponse> retrySendMessage(List<MessageTaskDetailPo> messageTaskDetailPoList);
}
