package cn.xk.xcode.config;

import cn.xk.xcode.core.DefaultGlobalPayConfigurer;
import cn.xk.xcode.core.GlobalPayClientRegister;
import cn.xk.xcode.core.GlobalPayConfigurer;
import cn.xk.xcode.core.client.AbstractPayClient;
import cn.xk.xcode.core.client.mock.MockPayClient;
import cn.xk.xcode.core.client.wx.*;
import cn.xk.xcode.core.client.zfb.*;
import cn.xk.xcode.core.config.DefaultPayClientConfig;
import cn.xk.xcode.core.config.PayClientConfig;
import cn.xk.xcode.core.config.wx.WxPayClientConfig;
import cn.xk.xcode.core.config.zfb.AliPayClientConfig;
import cn.xk.xcode.core.factory.PayClientFactory;
import cn.xk.xcode.core.factory.impl.PayClientFactoryImp;
import cn.xk.xcode.enums.PayErrorCodeConstants;
import cn.xk.xcode.exception.core.ExceptionUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.stream.Collectors;

import static cn.xk.xcode.enums.PayErrorCodeConstants.PAY_CLIENT_CHANNEL_ALREADY_EXISTS;

/**
 * @Author xuk
 * @Date 2024/9/11 13:41
 * @Version 1.0.0
 * @Description PayConfig
 **/
@Configuration
public class PayConfig {

    @Bean
    @ConditionalOnMissingBean(GlobalPayConfigurer.class)
    public GlobalPayConfigurer payConfigRegister(){
        return new DefaultGlobalPayConfigurer();
    }

    @Bean
    public MockPayClient mockPayClient() {
        return new MockPayClient("mock", new DefaultPayClientConfig());
    }

    @Bean
    public WxAppPayClient wxAppPayClient(GlobalPayConfigurer globalPayConfigurer){
        WxPayClientConfig wxPayClientConfig = globalPayConfigurer.registerWxPayClientConfig();
        if (Objects.isNull(wxPayClientConfig)){
           return null;
        }
        return new WxAppPayClient(PayErrorCodeConstants.WX_APP, wxPayClientConfig);
    }

    @Bean
    public WxBarCodePayClient wxBarCodePayClient(GlobalPayConfigurer globalPayConfigurer){
        WxPayClientConfig wxPayClientConfig = globalPayConfigurer.registerWxPayClientConfig();
        if (Objects.isNull(wxPayClientConfig)){
            return null;
        }
        return new WxBarCodePayClient(PayErrorCodeConstants.WX_MICROPAY, wxPayClientConfig);
    }

    @Bean
    public WxH5PayClient wxH5PayClient(GlobalPayConfigurer globalPayConfigurer){
        WxPayClientConfig wxPayClientConfig = globalPayConfigurer.registerWxPayClientConfig();
        if (Objects.isNull(wxPayClientConfig)){
            return null;
        }
        return new WxH5PayClient(PayErrorCodeConstants.WX_MWEB, wxPayClientConfig);
    }

    @Bean
    public WxLitePayClient wxLitePayClient(GlobalPayConfigurer globalPayConfigurer){
        WxPayClientConfig wxPayClientConfig = globalPayConfigurer.registerWxPayClientConfig();
        if (Objects.isNull(wxPayClientConfig)){
            return null;
        }
        return new WxLitePayClient(PayErrorCodeConstants.WX_JSAPI, wxPayClientConfig);
    }

    @Bean
    public WxPubPayClient wxPubPayClient(GlobalPayConfigurer globalPayConfigurer){
        WxPayClientConfig wxPayClientConfig = globalPayConfigurer.registerWxPayClientConfig();
        if (Objects.isNull(wxPayClientConfig)){
            return null;
        }
        return new WxPubPayClient(PayErrorCodeConstants.WX_JSAPI, wxPayClientConfig);
    }

    @Bean
    public WxQrCodePayClient wxQrCodePayClient(GlobalPayConfigurer globalPayConfigurer){
        WxPayClientConfig wxPayClientConfig = globalPayConfigurer.registerWxPayClientConfig();
        if (Objects.isNull(wxPayClientConfig)){
            return null;
        }
        return new WxQrCodePayClient(PayErrorCodeConstants.WX_NATIVE, wxPayClientConfig);
    }

    @Bean
    public AliAppPayClient aliAppPayClient(GlobalPayConfigurer globalPayConfigurer){
        AliPayClientConfig aliPayClientConfig = globalPayConfigurer.registerAliPayClientConfig();
        if (Objects.isNull(aliPayClientConfig)){
            return null;
        }
        return new AliAppPayClient(PayErrorCodeConstants.ALI_APP, aliPayClientConfig);
    }

    @Bean
    public AliBarCodePayClient aliBarCodePayClient(GlobalPayConfigurer globalPayConfigurer){
        AliPayClientConfig aliPayClientConfig = globalPayConfigurer.registerAliPayClientConfig();
        if (Objects.isNull(aliPayClientConfig)){
            return null;
        }
        return new AliBarCodePayClient(PayErrorCodeConstants.ALI_BAR, aliPayClientConfig);
    }

    @Bean
    public AliPcPayClient aliPcPayClient(GlobalPayConfigurer globalPayConfigurer){
        AliPayClientConfig aliPayClientConfig = globalPayConfigurer.registerAliPayClientConfig();
        if (Objects.isNull(aliPayClientConfig)){
            return null;
        }
        return new AliPcPayClient(PayErrorCodeConstants.ALI_PC, aliPayClientConfig);
    }

    @Bean
    public AliQrCodePayClient aliQrCodePayClient(GlobalPayConfigurer globalPayConfigurer){
        AliPayClientConfig aliPayClientConfig = globalPayConfigurer.registerAliPayClientConfig();
        if (Objects.isNull(aliPayClientConfig)){
            return null;
        }
        return new AliQrCodePayClient(PayErrorCodeConstants.ALI_QR_CODE, aliPayClientConfig);
    }

    @Bean
    public AliWapPayClient aliWapPayClient(GlobalPayConfigurer globalPayConfigurer){
        AliPayClientConfig aliPayClientConfig = globalPayConfigurer.registerAliPayClientConfig();
        if (Objects.isNull(aliPayClientConfig)){
            return null;
        }
        return new AliWapPayClient(PayErrorCodeConstants.ALI_WAP, aliPayClientConfig);
    }

    @Bean
    public <Config extends PayClientConfig> PayClientFactory payClientFactory(GlobalPayConfigurer globalPayConfigurer, List<AbstractPayClient<Config>> clients){
        PayClientFactory payClientFactory = new PayClientFactoryImp();
        clients.forEach(client -> payClientFactory.registerPayClient(client.getChannel(), client));
        GlobalPayClientRegister globalPayClientRegister = new GlobalPayClientRegister(new ArrayList<>());
        globalPayConfigurer.registerPayClient(globalPayClientRegister);
        Set<String> collect = clients.stream().map(AbstractPayClient::getChannel).collect(Collectors.toSet());
        globalPayClientRegister.OTHER_PAY_CLIENTS.forEach(client ->{
            if (collect.contains(client.getChannel())){
                ExceptionUtil.castServiceException(PAY_CLIENT_CHANNEL_ALREADY_EXISTS, client.getChannel());
            }
            payClientFactory.registerPayClient(client.getChannel(), client);
        });
        return payClientFactory;
    }
}
