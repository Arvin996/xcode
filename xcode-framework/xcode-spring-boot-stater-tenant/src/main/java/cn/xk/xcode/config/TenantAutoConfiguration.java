package cn.xk.xcode.config;

import cn.xk.xcode.core.aop.TenantIgnoreAop;
import cn.xk.xcode.core.db.CustomTenantFactory;
import cn.xk.xcode.core.filter.TenantFilter;
import cn.xk.xcode.core.rpc.TenantFeignInterceptor;
import com.mybatisflex.core.tenant.TenantFactory;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/12/17 15:51
 * @Version 1.0.0
 * @Description TenantAutoConfiguration
 **/
@Configuration
@ConditionalOnProperty(value = "xcode.tenant.enabled", havingValue = "true")
@EnableConfigurationProperties(TenantProperties.class)
public class TenantAutoConfiguration {


    @Bean
    public TenantIgnoreAop tenantIgnoreAop() {
        return new TenantIgnoreAop();
    }

    @Bean
    public TenantFactory tenantFactory(TenantProperties tenantProperties) {
        return new CustomTenantFactory(tenantProperties);
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new TenantFeignInterceptor();
    }

    @Bean
    public FilterRegistrationBean<TenantFilter> tenantSecurityWebFilter(TenantProperties tenantProperties) {
        FilterRegistrationBean<TenantFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TenantFilter(tenantProperties));
        registrationBean.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        registrationBean.setName("tenantFilter");
        return registrationBean;
    }
}
