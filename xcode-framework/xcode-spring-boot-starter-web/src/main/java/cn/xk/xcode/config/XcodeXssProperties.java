package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/12/4 14:47
 * @Version 1.0.0
 * @Description XcodeXssProperties
 **/
@ConfigurationProperties(value = "xcode.xss")
@Data
public class XcodeXssProperties {

    private boolean enabled;
    private List<String> excludeList;
    private List<String> includeList;
}
