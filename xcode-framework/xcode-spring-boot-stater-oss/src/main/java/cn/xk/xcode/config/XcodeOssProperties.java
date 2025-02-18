package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author xuk
 * @Date 2025/2/18 13:41
 * @Version 1.0.0
 * @Description XcodeOssProperties
 **/
@Data
@ConfigurationProperties("xcode.oss")
public class XcodeOssProperties {

    // 单位秒
    private static final Long DEFAULT_EXPIRE_TIME = 3600L;

    private boolean isMinIoCClient = false;

    // 是否使用https
    private boolean isHttps = true;

    private Long preSingerExpireTime = DEFAULT_EXPIRE_TIME;

    // 访问站点
    private String endpoint;

    // access_key
    private String accessKey;

    // secret_key
    private String secretKey;

    // 域名
    private String domain;

    // 文件名称方式
    private FileNamingEnum naming = FileNamingEnum.ORIGINAL;

    public enum FileNamingEnum {
        ORIGINAL,
        UUID
    }
}
