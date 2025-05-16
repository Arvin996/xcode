package cn.xk.xcode.entity.discard.account.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2025/3/10 15:26
 * @Version 1.0.0
 * @Description TencentSmsChannelAccount
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class TencentSmsChannelAccount extends BaseSmsChannelAccount{

    /**
     * api相关
     */
    public static final String url = "https://sms.tencentcloudapi.com";
    private String region;

    /**
     * 账号相关
     */
    private String secretId;
    private String secretKey;
    private String smsSdkAppId;
    private String templateId;
    private String signName;
}
