package cn.xk.xcode.core.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;
import java.util.Properties;

/**
 * @Author xuk
 * @Date 2024/12/4 13:56
 * @Version 1.0.0
 * @Description ValidatorConfig
 **/
@Configuration
public class ValidatorConfig {

    @Bean
    public MessageSource messageSource(){
        return new ResourceBundleMessageSource();
    }

    /**
     * 配置校验框架 快速返回模式
     */
    @Bean
    public Validator validator(MessageSource messageSource) {
        try (LocalValidatorFactoryBean factoryBean = new LocalValidatorFactoryBean()) {
            // 国际化
            factoryBean.setValidationMessageSource(messageSource);
            // 设置使用 HibernateValidator 校验器
            factoryBean.setProviderClass(HibernateValidator.class);
            Properties properties = new Properties();
            // 设置 快速异常返回
            properties.setProperty("hibernate.validator.fail_fast", "true");
            factoryBean.setValidationProperties(properties);
            // 加载配置
            factoryBean.afterPropertiesSet();
            return factoryBean.getValidator();
        }
    }
}
