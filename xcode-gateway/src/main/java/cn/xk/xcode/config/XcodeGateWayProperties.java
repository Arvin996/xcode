package cn.xk.xcode.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author xuk
 * @Date 2025/2/11 8:33
 * @Version 1.0.0
 * @Description XcodeGateWayProperties
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties("xcode.gateway")
public class XcodeGateWayProperties {
    private String routePrefix = "";
}
