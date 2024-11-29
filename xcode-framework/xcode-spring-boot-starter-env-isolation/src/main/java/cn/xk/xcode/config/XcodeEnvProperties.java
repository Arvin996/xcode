package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/11/29 8:50
 * @Version 1.0.0
 * @Description XcodeEnvProperties
 **/
@Component
@Data
@ConfigurationProperties("xcode.env.isolation.tag")
public class XcodeEnvProperties {

    private String tag;
}
