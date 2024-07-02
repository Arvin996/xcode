package cn.xk.xcode.core;

import cn.xk.xcode.interceptor.ConsumeMessageInterceptor;
import cn.xk.xcode.interceptor.SendMessageInterceptor;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/7/2 09:36
 * @Version 1.0
 * @Description MessageInterceptorHolder
 */
@RequiredArgsConstructor
public class MessageInterceptorHolder
{
    private final MessageInterceptorRegistry registry;

    public List<SendMessageInterceptor> getSendMessageInterceptor(){
        return registry.getSendMessageInterceptors();
    }

    public List<ConsumeMessageInterceptor> getConsumeMessageInterceptor(){
        return registry.getConsumeMessageInterceptors();
    }
}
