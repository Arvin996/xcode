package cn.xk.xcode.interceptor;

import cn.xk.xcode.message.MessageEntity;

/**
 * @Author xuk
 * @Date 2024/7/2 08:42
 * @Version 1.0
 * @Description SendMessageInterceptor
 */
public interface SendMessageInterceptor extends MessageInterceptor
{
    @Override
    default void consumeMessageBefore(MessageEntity message){

    }

    @Override
    default void consumeMessageAfter(MessageEntity message){

    }
}
