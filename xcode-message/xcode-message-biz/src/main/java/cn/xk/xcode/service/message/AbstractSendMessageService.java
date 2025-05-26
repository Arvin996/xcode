package cn.xk.xcode.service.message;

import cn.xk.xcode.entity.discard.task.MessageTask;
import cn.xk.xcode.pojo.CommonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author xuk
 * @Date 2025/5/19 9:27
 * @Version 1.0.0
 * @Description AbstractSendMessageService
 **/
@RequiredArgsConstructor
@Slf4j
public abstract class AbstractSendMessageService {

    protected abstract String sendType();

    public abstract CommonResult<?> dealMessage(MessageTask messageTask);

}
