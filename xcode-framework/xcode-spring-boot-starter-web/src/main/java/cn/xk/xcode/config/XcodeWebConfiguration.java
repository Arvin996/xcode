package cn.xk.xcode.config;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.I18nLocaleResolver;
import cn.xk.xcode.filter.XssFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import javax.servlet.DispatcherType;

/**
 * @Author xuk
 * @Date 2024/12/4 14:49
 * @Version 1.0.0
 * @Description XcodeWebConfiguration
 **/
@Configuration
@EnableConfigurationProperties(XcodeXssProperties.class)
public class XcodeWebConfiguration {

    @Bean
    public LocaleResolver localeResolver() {
        return new I18nLocaleResolver();
    }

    @Bean
    @ConditionalOnProperty(value = "xcode.xss.enabled", havingValue = "true")
    public FilterRegistrationBean<XssFilter> xssFilterRegistration(XcodeXssProperties xssProperties) {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter(xssProperties));
        registration.addUrlPatterns(xssProperties.getIncludeList().stream().filter(StrUtil::isNotBlank).toArray(String[]::new));
        registration.setName("xssFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        return registration;
    }

}
