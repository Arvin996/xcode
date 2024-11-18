package cn.xk.xcode.config;

import cn.xk.xcode.core.MessageInterceptorRegistry;

/**
 * @Author xuk
 * @Date 2024/7/2 08:55
 * @Version 1.0
 * @Description MessageInterceptorConfigurer
 */
public interface MessageInterceptorConfigurer {

    default void addSendMessageInterceptor(MessageInterceptorRegistry registry) {

    }

    default void addConsumeMessageInterceptor(MessageInterceptorRegistry registry) {

    }
}
