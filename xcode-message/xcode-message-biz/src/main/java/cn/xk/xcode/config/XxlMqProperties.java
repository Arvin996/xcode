package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author xuk
 * @Date 2025/3/5 17:16
 * @Version 1.0.0
 * @Description XxlMqProperties
 **/
@Data
@ConfigurationProperties("xcode.xxl.mq")
public class XxlMqProperties {

    private String adminAddress;

}
