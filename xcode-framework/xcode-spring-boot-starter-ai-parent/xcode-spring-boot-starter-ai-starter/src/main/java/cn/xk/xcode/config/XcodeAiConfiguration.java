package cn.xk.xcode.config;

import cn.xk.xcode.core.aop.AiPlatformAdvisor;
import cn.xk.xcode.core.aop.AiPlatformInterceptor;
import cn.xk.xcode.core.client.AiClient;
import cn.xk.xcode.core.context.XcodeAiModelContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @Author xuk
 * @Date 2025/2/14 9:00
 * @Version 1.0.0
 * @Description XcodeAiConfiguration
 **/
@Configuration
@EnableConfigurationProperties(XcodeAiProperties.class)
public class XcodeAiConfiguration {

    @Bean
    public XcodeAiModelContext xcodeAiModelContext() {
        return new XcodeAiModelContext(new HashMap<>(), new HashMap<>(), new HashMap<>());
    }

    @Bean
    public AiClient aiClient(XcodeAiModelContext xcodeAiModelContext) {
        return new AiClient(xcodeAiModelContext);
    }

    @Bean
    public AiPlatformInterceptor aiPlatformInterceptor() {
        return new AiPlatformInterceptor();
    }

    @Bean
    public AiPlatformAdvisor aiPlatformAdvisor(AiPlatformInterceptor advice) {
        return new AiPlatformAdvisor(advice);
    }
}
