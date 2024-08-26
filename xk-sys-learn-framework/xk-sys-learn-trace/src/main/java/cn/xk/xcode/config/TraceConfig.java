package cn.xk.xcode.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author xuk
 * @Date 2024/8/6 11:02
 * @Version 1.0E
 * @Description TraceConfig
 */
@Configuration
@EnableConfigurationProperties(TraceProperties.class)
@ConditionalOnProperty
public class TraceConfig {

}
