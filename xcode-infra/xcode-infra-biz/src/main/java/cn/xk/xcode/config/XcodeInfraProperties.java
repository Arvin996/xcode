package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2025/5/22 12:26
 * @Version 1.0.0
 * @Description XcodeInfraProperties
 **/
@ConfigurationProperties(prefix = "xcode.infra")
@Component
@Data
public class XcodeInfraProperties {

    private int fileProcessMaxFailCount = 3;
}
