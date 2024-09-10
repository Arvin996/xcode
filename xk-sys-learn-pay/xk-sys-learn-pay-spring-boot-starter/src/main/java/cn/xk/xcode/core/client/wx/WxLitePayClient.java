package cn.xk.xcode.core.client.wx;

import cn.xk.xcode.core.config.wx.WxPayClientConfig;

/**
* @Author xuk
* @Date 2024/9/10 22:21
* @Version 1.0.0
* @Description WxLitePayClient 微信小程序支付
**/
public class WxLitePayClient extends WxPubPayClient{

    public WxLitePayClient(String channel, WxPayClientConfig config) {
        super(channel, config);
    }
}
