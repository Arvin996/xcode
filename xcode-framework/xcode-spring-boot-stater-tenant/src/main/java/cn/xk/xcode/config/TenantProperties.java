package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.Set;

/**
 * @Author xuk
 * @Date 2024/12/17 15:41
 * @Version 1.0.0
 * @Description TenantProperties
 **/
@ConfigurationProperties(prefix = "xcode.tenant")
@Data
public class TenantProperties {

    private boolean enabled = true;

    private Set<String> ignoreUrls = Collections.emptySet();

    private Set<String> ignoreTables = Collections.emptySet();
}
