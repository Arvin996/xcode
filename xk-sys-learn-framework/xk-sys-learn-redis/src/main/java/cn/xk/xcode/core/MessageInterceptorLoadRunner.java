package cn.xk.xcode.core;

import cn.xk.xcode.config.MessageInterceptorConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/7/2 09:14
 * @Version 1.0
 * @Description MessageInterceptorLoadRunner
 */
@Component
@Slf4j
public class MessageInterceptorLoadRunner implements ApplicationRunner, ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, MessageInterceptorConfigurer> beans = applicationContext.getBeansOfType(MessageInterceptorConfigurer.class);
        if (beans.size() > 1) {
            log.error("MessageInterceptorConfigurer 只能有一个实现类");
        }
        MessageInterceptorRegistry registry = new MessageInterceptorRegistry(new ArrayList<>(), new ArrayList<>());
        if (beans.size() == 1) {
            beans.values().forEach(bean -> {
                bean.addConsumeMessageInterceptor(registry);
                bean.addSendMessageInterceptor(registry);
            });
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MessageInterceptorHolder.class);
            beanDefinitionBuilder.addConstructorArgValue(registry);
            BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) beanFactory;
            beanDefinitionRegistry.registerBeanDefinition("messageInterceptorHolder", beanDefinition);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
