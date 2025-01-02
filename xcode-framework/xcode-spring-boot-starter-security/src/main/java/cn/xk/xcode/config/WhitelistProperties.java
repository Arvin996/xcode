package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "xcode.security.whitelist")
@Component
public class WhitelistProperties {

    private List<String> whitelist;

}