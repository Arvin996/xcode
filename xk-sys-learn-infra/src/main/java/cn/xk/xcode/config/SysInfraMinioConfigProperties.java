package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author xuk
 * @Date 2024/6/25 14:15
 * @Version 1.0
 * @Description SysInfraMinioConfigProperties
 */
@Data
@ConfigurationProperties("xk.sys.infra.minio")
public class SysInfraMinioConfigProperties
{
    private String endpoint;

    private String accessKey;

    private String secretKey;
}
