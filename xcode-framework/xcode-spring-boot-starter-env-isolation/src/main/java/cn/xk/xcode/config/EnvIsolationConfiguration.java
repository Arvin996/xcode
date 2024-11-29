package cn.xk.xcode.config;

import cn.xk.xcode.core.feign.EnvIsolationRequestInterceptor;
import cn.xk.xcode.core.filter.EnvIsolationFilter;
import cn.xk.xcode.core.loadbalancer.EnvIsolationLoadBalancerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClientsProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientSpecification;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/29 9:27
 * @Version 1.0.0
 * @Description EnvIsolationConfiguration
 **/
@Configuration
@ConditionalOnProperty("xcode.env.isolation.tag")
public class EnvIsolationConfiguration {

    private static final int DEFAULT_ORDER = -12226;

    @Bean
    public LoadBalancerClientFactory loadBalancerClientFactory(LoadBalancerClientsProperties properties,
                                                               ObjectProvider<List<LoadBalancerClientSpecification>> configurations) {
        EnvIsolationLoadBalancerFactory clientFactory = new EnvIsolationLoadBalancerFactory(properties);
        clientFactory.setConfigurations(configurations.getIfAvailable(Collections::emptyList));
        return clientFactory;
    }

    @Bean
    public EnvIsolationRequestInterceptor envIsolationRequestInterceptor() {
        return new EnvIsolationRequestInterceptor();
    }

    @Bean
    public FilterRegistrationBean<EnvIsolationFilter> envWebFilterFilter() {
        EnvIsolationFilter filter = new EnvIsolationFilter();
        FilterRegistrationBean<EnvIsolationFilter> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(DEFAULT_ORDER);
        return bean;
    }

}
