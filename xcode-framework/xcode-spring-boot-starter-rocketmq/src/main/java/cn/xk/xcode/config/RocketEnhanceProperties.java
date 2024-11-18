package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rocketmq.enhance")
@Data
public class RocketEnhanceProperties {

    private boolean enabledIsolation;

    private String env;
}
