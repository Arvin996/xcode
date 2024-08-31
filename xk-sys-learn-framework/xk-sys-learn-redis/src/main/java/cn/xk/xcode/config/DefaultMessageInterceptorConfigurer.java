package cn.xk.xcode.config;

import cn.xk.xcode.core.MessageInterceptorRegistry;

/**
 * @Author Administrator
 * @Date 2024/8/31 10:44
 * @Version V1.0.0
 * @Description DefaultMessageInterceptorConfigurer
 */
public class DefaultMessageInterceptorConfigurer implements MessageInterceptorConfigurer {

    @Override
    public void addSendMessageInterceptor(MessageInterceptorRegistry registry) {
        MessageInterceptorConfigurer.super.addSendMessageInterceptor(registry);
    }

    @Override
    public void addConsumeMessageInterceptor(MessageInterceptorRegistry registry) {
        MessageInterceptorConfigurer.super.addConsumeMessageInterceptor(registry);
    }
}
