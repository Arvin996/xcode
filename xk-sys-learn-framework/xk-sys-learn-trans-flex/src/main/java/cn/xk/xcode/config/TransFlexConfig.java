package cn.xk.xcode.config;

import cn.xk.xcode.core.aop.TransFlexAnnotationAdvisor;
import cn.xk.xcode.core.aop.TransFlexAnnotationInterceptor;
import cn.xk.xcode.core.handler.InitTransEnumsEventHandler;
import cn.xk.xcode.core.service.FlexTransService;
import cn.xk.xcode.support.enums.DefaultTransEnumConfigurer;
import cn.xk.xcode.support.enums.GlobalEnumsContext;
import cn.xk.xcode.support.enums.TransEnumConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/8/16 10:01
 * @description
 */
@Configuration
public class TransFlexConfig {

    @Bean
    @ConditionalOnMissingBean(TransEnumConfigurer.class)
    public TransEnumConfigurer transEnumConfigurer(){
        return new DefaultTransEnumConfigurer();
    }

    @Bean
    public GlobalEnumsContext globalEnumsContext() {
        return InitTransEnumsEventHandler.init();
    }

    @Bean
    public TransFlexAnnotationInterceptor transFlexAnnotationInterceptor(FeignClientBuilder feignClientBuilder,
                                                                         GlobalEnumsContext globalEnumsContext,
                                                                         FlexTransService flexTransService){
        return new TransFlexAnnotationInterceptor(globalEnumsContext, feignClientBuilder, flexTransService);
    }

    @Bean
    public TransFlexAnnotationAdvisor transFlexAnnotationAdvisor(TransFlexAnnotationInterceptor transFlexAnnotationInterceptor) {
        return new TransFlexAnnotationAdvisor(transFlexAnnotationInterceptor);
    }

    @Bean
    @Primary
    public FeignClientBuilder feignClientBuilder(ApplicationContext applicationContext){
        return new FeignClientBuilder(applicationContext);
    }

    @Bean
    public FlexTransService flexTransService(){
        return new FlexTransService();
    }
}
