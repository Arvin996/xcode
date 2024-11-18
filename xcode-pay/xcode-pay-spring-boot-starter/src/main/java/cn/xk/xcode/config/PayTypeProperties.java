package cn.xk.xcode.config;

import cn.xk.xcode.core.config.wx.WxPayClientConfig;
import cn.xk.xcode.core.config.zfb.AliPayClientConfig;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/9/20 15:47
 * @Version 1.0.0
 * @Description PayTypeProperties
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "pay.client.config")
public class PayTypeProperties {

    private zfb zfb;
    private wx wx;

    @Getter
    @Setter
    public static class wx {
        private String appId;
        private String mchId;
        private String apiVersion;
        private V2 wxV2;
        private V3 wxV3;
    }

    @Getter
    @Setter
    public static class V2 {
        private String mchKey;
        private String keyContent;
    }


    @Getter
    @Setter
    public static class V3 {
        private String privateKeyContent;
        private String privateCertContent;
        private String apiV3Key;
    }

    @Getter
    @Setter
    public static class zfb {

        /**
         * 公钥类型 - 公钥加签
         */
        public static final Integer MODE_PUBLIC_KEY = 1;

        /**
         * 公钥类型 - 证书模式 公钥证书模式加签
         */
        public static final Integer MODE_PUBLIC_KEY_AND_CERTIFICATE = 2;

        private String serverUrl;
        private String appId;
        private Integer mode;
        private String signType =  AliPayClientConfig.SIGN_TYPE_DEFAULT;
        private cert certMode;
        private publicKey publicKeyMode;
    }

    @Getter
    @Setter
    public static class publicKey{
        private String privateKey;
        private String alipayPublicKey;
    }

    @Getter
    @Setter
    public static class cert{
        private String appCertContent;
        private String alipayPublicCertContent;
        private String rootCertContent;
    }
}
