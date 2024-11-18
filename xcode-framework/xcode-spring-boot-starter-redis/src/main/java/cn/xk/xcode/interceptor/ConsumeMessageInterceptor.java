package cn.xk.xcode.interceptor;

import cn.xk.xcode.message.MessageEntity;

/**
 * @Author xuk
 * @Date 2024/7/2 08:47
 * @Version 1.0
 * @Description ConsumeMessageInterceptor
 */
@SuppressWarnings("all")
public interface ConsumeMessageInterceptor extends MessageInterceptor {
    @Override
    default void sendMessageBefore(MessageEntity message){

    }

    @Override
    default void sendMessageAfter(MessageEntity message){

    }
}
