package cn.xk.xcode.core.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/9/6 21:40
 * @description
 */
@Data
@NoArgsConstructor
public class DefaultPayClientConfig implements PayClientConfig {

    private String name;

    @JsonIgnore
    public DefaultPayClientConfig getDefaultPayClientConfig() {
        DefaultPayClientConfig defaultPayClientConfig = new DefaultPayClientConfig();
        defaultPayClientConfig.setName("default");
        return defaultPayClientConfig;
    }
}
