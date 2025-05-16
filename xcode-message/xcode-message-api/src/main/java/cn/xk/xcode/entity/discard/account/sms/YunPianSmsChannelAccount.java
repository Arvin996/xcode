package cn.xk.xcode.entity.discard.account.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2025/3/10 15:24
 * @Version 1.0.0
 * @Description YunPianSmsChannelAccount
 **/
@EqualsAndHashCode(callSuper = true)
@Data
public class YunPianSmsChannelAccount extends BaseSmsChannelAccount {

    /**
     * apikey
     */
    private String apikey;

    /**
     * tplId
     */
    private String tplId;

    /**
     * api相关
     */
    public static final String URL = "https://sms.yunpian.com/v2/sms/batch_send.json";
}
