package cn.xk.xcode.core;

import cn.xk.xcode.interceptor.ConsumeMessageInterceptor;
import cn.xk.xcode.interceptor.SendMessageInterceptor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/7/2 08:56
 * @Version 1.0
 * @Description MessageInterceptorRegistry
 */
@Getter
@RequiredArgsConstructor
public class MessageInterceptorRegistry
{
    private final List<SendMessageInterceptor> sendMessageInterceptors;
    private final List<ConsumeMessageInterceptor> consumeMessageInterceptors;

    public MessageInterceptorRegistry addSendMessageInterceptor(SendMessageInterceptor sendMessageInterceptor){
        sendMessageInterceptors.add(sendMessageInterceptor);
        return this;
    }

    public MessageInterceptorRegistry addConsumeMessageInterceptor(ConsumeMessageInterceptor consumeMessageInterceptor){
        consumeMessageInterceptors.add(consumeMessageInterceptor);
        return this;
    }
}
