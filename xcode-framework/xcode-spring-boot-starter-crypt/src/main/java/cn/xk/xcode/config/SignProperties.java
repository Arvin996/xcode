package cn.xk.xcode.config;

import cn.hutool.crypto.asymmetric.SignAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/10/26 12:18
 * @description XkSysSignProperties
 */
@Data
@ConfigurationProperties("xcode.sign")
@Component
public class SignProperties {
    private String publicKey;
    private String privateKey;
    private String publicKeyPemPath;
    private String privateKeyPemPath;
    private SignProperties.SignAlgType signAlgType = SignProperties.SignAlgType.MD5withRSA;
    private SignProperties.SignType signType = SignProperties.SignType.DEFAULT;
    private SignProperties.SignDateRule signDateRule =  SignProperties.SignDateRule.REQUEST_BODY_ONLY;
    private String separator = "&";
    private String signName = "sign";
    private SignProperties.SignLocation signLocation = SignProperties.SignLocation.BODY;
    private CryptProperties.ASYMMETRIC_KEY_SOURCE sourceKeyType = CryptProperties.ASYMMETRIC_KEY_SOURCE.APPLICATION_FILE;
    private Boolean isDecryptRequestParam = false;

    @Getter
    @AllArgsConstructor
    public enum SignAlgType {
        NONEwithRSA("NONEwithRSA", SignAlgorithm.NONEwithRSA),
        MD2withRSA("MD2withRSA", SignAlgorithm.MD2withRSA),
        MD5withRSA("MD5withRSA", SignAlgorithm.MD5withRSA),
        SHA1withRSA("SHA1withRSA", SignAlgorithm.SHA1withRSA),
        SHA256withRSA("SHA256withRSA", SignAlgorithm.SHA256withRSA),
        SHA384withRSA("SHA384withRSA", SignAlgorithm.SHA384withRSA),
        SHA512withRSA("SHA512withRSA", SignAlgorithm.SHA512withRSA),
        NONEwithDSA("NONEwithDSA", SignAlgorithm.NONEwithDSA),
        SHA1withDSA("SHA1withDSA", SignAlgorithm.SHA1withDSA),
        NONEwithECDSA("NONEwithECDSA", SignAlgorithm.NONEwithECDSA),
        SHA1withECDSA("SHA1withECDSA", SignAlgorithm.SHA1withECDSA),
        SHA256withECDSA("SHA256withECDSA", SignAlgorithm.SHA256withECDSA),
        SHA384withECDSA("SHA384withECDSA", SignAlgorithm.SHA384withECDSA),
        SHA512withECDSA("SHA512withECDSA", SignAlgorithm.SHA512withECDSA),
        SHA256withRSA_PSS("SHA256WithRSA/PSS", SignAlgorithm.SHA256withRSA_PSS),
        SHA384withRSA_PSS("SHA384WithRSA/PSS", SignAlgorithm.SHA384withRSA_PSS),
        SHA512withRSA_PSS("SHA512WithRSA/PSS", SignAlgorithm.SHA512withRSA_PSS);
        private final String type;
        private final SignAlgorithm signAlgorithm;
    }

    @Getter
    @AllArgsConstructor
    public enum SignType {
        COSTUME("0", "用户自定义签名算法"),
        DEFAULT("1", "使用框架支持的默认签名算法");
        private final String type;
        private final String desc;
    }

    @AllArgsConstructor
    @Getter
    public enum SignDateRule {
        REQUEST_BODY_ONLY("body", "仅对请求体进行签名"),
        REQUEST_URL_PARAMS_ONLY("params", "仅对请求参数进行签名"),
        REQUEST_BODY_AND_URL_PARAMS("all", "对请求体和请求参数进行签名");
        private final String rule;
        private final String desc;
    }

    @AllArgsConstructor
    @Getter
    public enum SignLocation {
        HEADER ("header", "请求头"),
        PARAM("params", "请求参数"),
        BODY("body", "请求体");
        private final String location;
        private final String desc;
    }
}
