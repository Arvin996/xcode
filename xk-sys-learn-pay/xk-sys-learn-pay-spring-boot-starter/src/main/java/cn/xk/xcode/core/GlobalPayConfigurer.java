package cn.xk.xcode.core;

import cn.xk.xcode.core.config.wx.WxPayClientConfig;
import cn.xk.xcode.core.config.zfb.AliPayClientConfig;

/**
 * @Author xuk
 * @Date 2024/9/12 8:42
 * @Version 1.0.0
 * @Description GlobalPayConfigurer
 **/
public interface GlobalPayConfigurer {
    AliPayClientConfig registerAliPayClientConfig();
    WxPayClientConfig registerWxPayClientConfig();
    void registerPayClient(GlobalPayClientRegister register);
}
