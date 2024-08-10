package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author xuk
 * @Date 2024/7/16 15:04
 * @Version 1.0
 * @Description BizMessageProperties
 */
@Data
@ConfigurationProperties(prefix = "xk.biz.message")
public class BizMessageProperties
{
    private String dbUrl;

    private String password;

    private String username;
}
