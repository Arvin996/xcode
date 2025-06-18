package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2025/6/10 9:27
 * @Version 1.0.0
 * @Description XcodeThirdLoginProperties
 **/
@Component
@Data
@ConfigurationProperties("xcode.security.third")
public class XcodeThirdLoginProperties {

    private CommonProperties feiShu;
    private CommonProperties dingDing;
    private CommonProperties gitee;
    private CommonProperties github;
    private AlipayProperties alipay;
    private QQProperties qq;
    private CommonProperties google;

    @Data
    public static class CommonProperties {
        private String clientId;
        private String clientSecret;
        private String callbackUrl;
    }

    @Data
    public static class AlipayProperties {
        private String appId;
        private String callbackUrl;
    }

    @Data
    public static class QQProperties {
        private String appId;
        private String appKey;
        private String callbackUrl;
    }
}
