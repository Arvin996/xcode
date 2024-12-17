package cn.xk.xcode.config;

import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.xk.xcode.core.enums.AlgEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/10/24 9:40
 * @Version 1.0.0
 * @Description XkSysCryptProperties
 **/
@Data
@ConfigurationProperties("xcode.crypt")
@Component
public class CryptProperties {

    private AES aes;
    private SM4 sm4;
    private SM2 sm2;
    private DES des;
    private RC4 rc4;
    private RSA rsa;
    private ECIES ecies;
    private DESEde desEde;
    private ChaCha20 chacha20;
    private CipherType cipherType = CipherType.DEFAULT;
    private AlgEnum algEnum;
    private CostumeAlg costumeAlg;

    @Data
    public static class CostumeAlg {
        private String publicKeyPemPath;
        private String privateKeyPemPath;
        private String secretKey;
        private CostumeAlgEnum algEnum = CostumeAlgEnum.ASYMMETRIC;
    }

    @Getter
    @AllArgsConstructor
    public enum CostumeAlgEnum {
        SYMMETRIC("对称加密"),
        ASYMMETRIC("非对称加密");
        private final String type;
    }

    @AllArgsConstructor
    @Getter
    public enum CipherType {
        COSTUME("costume", "自定义加解密算法"),
        DEFAULT("default", "使用框架支持的默认加解密算法");
        private final String type;
        private final String desc;
    }

    @Data
    public static class SM2 {
        private String publicKey;
        private String privateKey;
        private String publicKeyPemPathName;
        private String privateKeyPemPathName;
        private ASYMMETRIC_KEY_SOURCE sourceKeyType = ASYMMETRIC_KEY_SOURCE.APPLICATION_FILE;
    }

    @Data
    public static class ECIES {
        private String publicKey;
        private String privateKey;
        private String publicKeyPemPathName;
        private String privateKeyPemPathName;
        private ASYMMETRIC_KEY_SOURCE sourceKeyType = ASYMMETRIC_KEY_SOURCE.APPLICATION_FILE;
    }

    @Data
    public static class RSA {
        private String publicKey;
        private String privateKey;
        private String publicKeyPemPathName;
        private String privateKeyPemPathName;
        private ASYMMETRIC_KEY_SOURCE sourceKeyType = ASYMMETRIC_KEY_SOURCE.APPLICATION_FILE;
        private RSA_TYPE rsaType = RSA_TYPE.RSA;
    }

    @Getter
    @AllArgsConstructor
    public enum ASYMMETRIC_KEY_SOURCE {
        PERM_FILE("0", "使用本地文件进行存储"),
        APPLICATION_FILE("1", "以base64编码直接放在服务的application配置文件中");
        private final String type;
        private final String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum RSA_TYPE {
        RSA("RSA", AsymmetricAlgorithm.RSA),
        RSA_ECB_PKCS1("RSA/ECB/PKCS1Padding", AsymmetricAlgorithm.RSA_ECB_PKCS1),
        RSA_ECB("RSA/ECB/NoPadding", AsymmetricAlgorithm.RSA_ECB),
        RSA_None("RSA/None/NoPadding", AsymmetricAlgorithm.RSA_None);
        private final String type;
        private final AsymmetricAlgorithm algorithm;
    }

    @Data
    public static class RC4 {
        private String key;
    }

    @Data
    public static class DES {
        private String key;
        private String iv;
        private Mode mode = Mode.NONE;
        private Padding padding = Padding.NoPadding;
    }

    @Data
    public static class ChaCha20 {
        private String key;
        private String iv;
    }

    @Data
    public static class AES {
        private String key;
        private String iv;
        private Mode mode = Mode.NONE;
        private Padding padding = Padding.NoPadding;
    }

    @Data
    public static class DESEde {
        private String key;
        private String iv;
        private Mode mode = Mode.NONE;
        private Padding padding = Padding.NoPadding;
    }

    @Data
    public static class SM4 {
        private String key;
        private String iv;
        private Mode mode = Mode.NONE;
        private Padding padding = Padding.NoPadding;
    }

    @Getter
    @AllArgsConstructor
    public enum Mode {
        NONE("none", cn.hutool.crypto.Mode.NONE),
        CBC("cbc", cn.hutool.crypto.Mode.CBC),
        CFB("cfb", cn.hutool.crypto.Mode.CFB),
        CTR("ctr", cn.hutool.crypto.Mode.CTR),
        CTS("cts", cn.hutool.crypto.Mode.CTS),
        ECB("ecb", cn.hutool.crypto.Mode.ECB),
        OFB("ofb", cn.hutool.crypto.Mode.OFB),
        PCBC("pcbc", cn.hutool.crypto.Mode.PCBC);
        private final String mode;
        private final cn.hutool.crypto.Mode MODE_TYPE;
    }

    @Getter
    @AllArgsConstructor
    public enum Padding {
        NoPadding("none", cn.hutool.crypto.Padding.NoPadding),
        ZeroPadding("zero", cn.hutool.crypto.Padding.ZeroPadding),
        ISO10126Padding("iso10126", cn.hutool.crypto.Padding.ISO10126Padding),
        OAEPPadding("oaep", cn.hutool.crypto.Padding.OAEPPadding),
        PKCS1Padding("pkcs1", cn.hutool.crypto.Padding.PKCS1Padding),
        PKCS5Padding("pkcs5", cn.hutool.crypto.Padding.PKCS5Padding),
        SSL3Padding("ssl3", cn.hutool.crypto.Padding.SSL3Padding);
        private final String padding;
        private final cn.hutool.crypto.Padding PADDING_TYPE;
    }
}
