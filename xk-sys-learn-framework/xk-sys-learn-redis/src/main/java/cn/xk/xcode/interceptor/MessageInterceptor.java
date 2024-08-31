package cn.xk.xcode.interceptor;

import cn.xk.xcode.message.MessageEntity;

/**
 * @Author xuk
 * @Date 2024/7/1 16:34
 * @Version 1.0
 * @Description MessageInterceptor
 */
public interface MessageInterceptor {
    void sendMessageBefore(MessageEntity message);

    void sendMessageAfter(MessageEntity message);

    void consumeMessageBefore(MessageEntity message);

    void consumeMessageAfter(MessageEntity message);
}
