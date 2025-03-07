package cn.xk.xcode.handler;

import cn.xk.xcode.entity.MessageChannelEnums;

/**
 * @Author xuk
 * @Date 2025/3/5 17:04
 * @Version 1.0.0
 * @Description AbstractMessageHandler
 **/
public abstract class AbstractMessageHandler {

    public abstract boolean doHandleMessage();


    public abstract MessageChannelEnums channel();

    public void validate() {
    }


}
