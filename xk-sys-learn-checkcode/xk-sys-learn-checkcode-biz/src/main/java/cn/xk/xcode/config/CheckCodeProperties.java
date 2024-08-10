package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/7/31 20:18
 * @Version 1.0
 * @Description CheckCodeProperties
 */
@Data
@ConfigurationProperties("xk.sys.checkcode")
public class CheckCodeProperties
{
    private String cacheType = "redis";

    private Integer expiredTime = 60;

    private Integer codeLength = 4;

    private Map<String, CheckCodeSendTypeConfig> sendType;
}
