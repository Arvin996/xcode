package cn.xk.xcode.config;

import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.StringUtils;

/**
 * @Author xuk
 * @Date 2024/7/3 10:21
 * @Version 1.0
 * @Description EnvironmentIsolationConfig
 */
@RequiredArgsConstructor
public class EnvironmentIsolationConfig implements BeanPostProcessor {

    private final RocketEnhanceProperties rocketEnhanceProperties;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DefaultRocketMQListenerContainer) {
            DefaultRocketMQListenerContainer container = (DefaultRocketMQListenerContainer) bean;
            if (rocketEnhanceProperties.isEnabledIsolation() && StringUtils.hasText(rocketEnhanceProperties.getEnv())) {
                container.setTopic(String.join("_", container.getTopic(), rocketEnhanceProperties.getEnv()));
            }
            return container;
        }
        return bean;
    }

}
