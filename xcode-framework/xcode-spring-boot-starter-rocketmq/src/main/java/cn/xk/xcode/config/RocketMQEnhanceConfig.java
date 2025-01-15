package cn.xk.xcode.config;

import cn.xk.xcode.core.RocketMQEnhanceTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/7/3 10:01
 * @Version 1.0
 * @Description RocketMQEnhanceAutoConfiguration
 */
@EnableConfigurationProperties(RocketEnhanceProperties.class)
@Configuration
public class RocketMQEnhanceConfig {

    @Resource
    private RocketEnhanceProperties rocketEnhanceProperties;

    @Bean
    public RocketMQEnhanceTemplate rocketMQEnhanceTemplate(RocketMQTemplate rocketMQTemplate){
        return new RocketMQEnhanceTemplate(rocketMQTemplate, rocketEnhanceProperties);
    }

    /**
     * 解决RocketMQ Jackson不支持Java时间类型配置
     */
    @Bean
    @Primary
    public RocketMQMessageConverter enhanceRocketMQMessageConverter(){
        RocketMQMessageConverter converter = new RocketMQMessageConverter();
        CompositeMessageConverter compositeMessageConverter = (CompositeMessageConverter) converter.getMessageConverter();
        List<MessageConverter> messageConverterList = compositeMessageConverter.getConverters();
        for (MessageConverter messageConverter : messageConverterList) {
            if(messageConverter instanceof MappingJackson2MessageConverter){
                MappingJackson2MessageConverter jackson2MessageConverter =  (MappingJackson2MessageConverter) messageConverter;
                ObjectMapper objectMapper = jackson2MessageConverter.getObjectMapper();
                objectMapper.registerModules(new JavaTimeModule());
            }
        }
        return converter;
    }

    /**
     * 环境隔离配置
     */
    @Bean
    @ConditionalOnProperty(name="rocketmq.enhance.enabledIsolation", havingValue="true")
    public EnvironmentIsolationConfig environmentSetup(RocketEnhanceProperties rocketEnhanceProperties){
        return new EnvironmentIsolationConfig(rocketEnhanceProperties);
    }
}
