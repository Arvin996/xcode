package cn.xk.xcode.config;

import cn.xk.xcode.core.*;
import cn.xk.xcode.core.factory.AbstractThreadPoolFactory;
import cn.xk.xcode.core.factory.CommonThreadPoolFactory;
import cn.xk.xcode.core.factory.SingleThreadPoolFactory;
import cn.xk.xcode.core.factory.ThreadPoolProduceDecider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/7 14:20
 * @Version 1.0.0
 * @Description XcodeThreadPoolConfiguration
 **/
@Configuration
public class XcodeThreadPoolConfiguration {

    @Bean
    public ThreadPoolRegister threadPoolRegister(ThreadPoolExecutorAllShutDownProcessor threadPoolExecutorAllShutDownProcessor) {
        return new ThreadPoolRegister(threadPoolExecutorAllShutDownProcessor);
    }

    @Bean
    public ThreadPoolExecutorAllShutDownProcessor threadPoolExecutorAllShutDownProcessor() {
        return new ThreadPoolExecutorAllShutDownProcessor();
    }

    @Bean
    public AbstractThreadPoolFactory singleThreadPoolFactory(ThreadPoolRegister register) {
        return new SingleThreadPoolFactory(register);
    }

    @Bean
    public AbstractThreadPoolFactory commonThreadPoolFactory(ThreadPoolRegister register) {
        return new CommonThreadPoolFactory(register);
    }

    @Bean
    public ThreadPoolProduceDecider threadPoolProduceDecider(List<AbstractThreadPoolFactory> threadPoolProducerList) {
        return new ThreadPoolProduceDecider(threadPoolProducerList);
    }

    @Bean
    @ConditionalOnMissingBean
    public ThreadPoolExecutorsUniqueCodeLoader threadPoolExecutorsUniqueCodeLoader() {
        return new DefaultThreadPoolExecutorsUniqueCodeLoader();
    }

    @Bean
    public ThreadPoolExecutorHolder threadPoolExecutorHolder(ThreadPoolExecutorsUniqueCodeLoader loader, ThreadPoolProduceDecider decider) {
        return new ThreadPoolExecutorHolder(loader, decider);
    }
}
