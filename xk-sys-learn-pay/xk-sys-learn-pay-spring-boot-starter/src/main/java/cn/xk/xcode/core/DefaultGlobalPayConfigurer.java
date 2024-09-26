package cn.xk.xcode.core;

import cn.xk.xcode.config.PayTypeProperties;
import cn.xk.xcode.core.config.wx.WxPayClientConfig;
import cn.xk.xcode.core.config.zfb.AliPayClientConfig;

/**
 * @Author xuk
 * @Date 2024/9/12 8:44
 * @Version 1.0.0
 * @Description DefaultGlobalPayConfigurer
 **/
public class DefaultGlobalPayConfigurer implements GlobalPayConfigurer{

    @Override
    public void registerPayClient(GlobalPayClientRegister register) {
    }
}
