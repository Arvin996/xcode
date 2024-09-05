package cn.xk.xcode.core.config.zfb;

import cn.xk.xcode.core.config.PayClientConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/9/3 19:29
 * @Version 1.0.0
 * @Description AliPayClientConfig
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliPayClientConfig implements PayClientConfig {

    /**
     * 公钥类型 - 公钥加签
     */
    public static final Integer MODE_PUBLIC_KEY = 1;

    /**
     * 公钥类型 - 证书模式 公钥证书模式加签
     */
    public static final Integer MODE_PUBLIC_KEY_AND_CERTIFICATE = 2;

    /**
     * 签名算法类型 - RSA
     */
    public static final String SIGN_TYPE_DEFAULT = "RSA2";


    /**
     * 网关地址
     * 1. <a href="https://openapi.alipay.com/gateway.do">生产环境</a>
     * 2. <a href="https://openapi-sandbox.dl.alipaydev.com/gateway.do">沙箱环境</a>
     */
    @NotBlank(message = "网关地址不能为空", groups = {ModePublicKey.class, ModePublicKeyAndCertificate.class})
    private String serverUrl;

    /**
     * 开放平台上创建的应用的 ID
     */
    @NotBlank(message = "开放平台上创建的应用的 ID不能为空", groups = {ModePublicKey.class, ModePublicKeyAndCertificate.class})
    private String appId;

    /**
     * 签名算法类型
     */
    @NotBlank(message = "签名算法类型不能为空", groups = {ModePublicKey.class, ModePublicKeyAndCertificate.class})
    private String signType = SIGN_TYPE_DEFAULT;

    /**
     * 公钥类型
     * 1. {@link #MODE_PUBLIC_KEY} 情况，privateKey + alipayPublicKey
     * 2. {@link #MODE_PUBLIC_KEY_AND_CERTIFICATE} 情况，appCertContent + alipayPublicCertContent + rootCertContent
     */
    @NotNull(message = "公钥类型不能为空", groups = {ModePublicKey.class, ModePublicKeyAndCertificate.class})
    private Integer mode;

    // ========== 公钥模式 ==========
    /**
     * 商户私钥
     */
    @NotBlank(message = "商户私钥不能为空", groups = {ModePublicKey.class})
    private String privateKey;

    /**
     * 支付宝公钥字符串
     */
    @NotBlank(message = "支付宝公钥字符串不能为空", groups = {ModePublicKey.class})
    private String alipayPublicKey;

    // ========== 证书模式 ==========

    /**
     * 指定商户公钥应用证书内容字符串
     */
    @NotBlank(message = "指定商户公钥应用证书内容不能为空", groups = {ModePublicKeyAndCertificate.class})
    private String appCertContent;

    /**
     * 指定支付宝公钥证书内容字符串
     */
    @NotBlank(message = "指定支付宝公钥证书内容不能为空", groups = {ModePublicKeyAndCertificate.class})
    private String alipayPublicCertContent;

    /**
     * 指定根证书内容字符串
     */
    @NotBlank(message = "指定根证书内容字符串不能为空", groups = {ModePublicKeyAndCertificate.class})
    private String rootCertContent;

    public interface ModePublicKey {
    }

    public interface ModePublicKeyAndCertificate {
    }
}
