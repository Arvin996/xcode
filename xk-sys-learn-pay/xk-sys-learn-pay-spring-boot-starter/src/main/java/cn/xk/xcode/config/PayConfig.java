package cn.xk.xcode.config;

import cn.hutool.core.util.ObjectUtil;
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
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.xk.xcode.enums.PayErrorCodeConstants.*;

/**
 * @Author xuk
 * @Date 2024/9/11 13:41
 * @Version 1.0.0
 * @Description PayConfig
 **/
@Configuration
@EnableConfigurationProperties(PayTypeProperties.class)
public class PayConfig {

    @Resource
    private PayTypeProperties payTypeProperties;

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
    @ConditionalOnBean(PayTypeProperties.class)
    public WxAppPayClient wxAppPayClient(){
        WxPayClientConfig wxPayClientConfig = getWxClientConfig();
        if (Objects.isNull(wxPayClientConfig)){
           return null;
        }
        return new WxAppPayClient(PayErrorCodeConstants.WX_APP, wxPayClientConfig);
    }

    @Bean
    @ConditionalOnBean(PayTypeProperties.class)
    public WxBarCodePayClient wxBarCodePayClient(){
        WxPayClientConfig wxPayClientConfig = getWxClientConfig();
        if (Objects.isNull(wxPayClientConfig)){
            return null;
        }
        return new WxBarCodePayClient(PayErrorCodeConstants.WX_MICROPAY, wxPayClientConfig);
    }

    @Bean
    @ConditionalOnBean(PayTypeProperties.class)
    public WxH5PayClient wxH5PayClient(){
        WxPayClientConfig wxPayClientConfig = getWxClientConfig();
        if (Objects.isNull(wxPayClientConfig)){
            return null;
        }
        return new WxH5PayClient(PayErrorCodeConstants.WX_MWEB, wxPayClientConfig);
    }

    @Bean
    @ConditionalOnBean(PayTypeProperties.class)
    public WxLitePayClient wxLitePayClient(){
        WxPayClientConfig wxPayClientConfig = getWxClientConfig();
        if (Objects.isNull(wxPayClientConfig)){
            return null;
        }
        return new WxLitePayClient(PayErrorCodeConstants.WX_JSAPI, wxPayClientConfig);
    }

    @Bean
    @ConditionalOnBean(PayTypeProperties.class)
    public WxPubPayClient wxPubPayClient(){
        WxPayClientConfig wxPayClientConfig = getWxClientConfig();
        if (Objects.isNull(wxPayClientConfig)){
            return null;
        }
        return new WxPubPayClient(PayErrorCodeConstants.WX_JSAPI, wxPayClientConfig);
    }

    @Bean
    @ConditionalOnBean(PayTypeProperties.class)
    public WxQrCodePayClient wxQrCodePayClient(){
        WxPayClientConfig wxPayClientConfig = getWxClientConfig();
        if (Objects.isNull(wxPayClientConfig)){
            return null;
        }
        return new WxQrCodePayClient(PayErrorCodeConstants.WX_NATIVE, wxPayClientConfig);
    }

    @Bean
    @ConditionalOnBean(PayTypeProperties.class)
    public AliAppPayClient aliAppPayClient(){
        AliPayClientConfig aliPayClientConfig = getAliPayClientConfig();
        if (Objects.isNull(aliPayClientConfig)){
            return null;
        }
        return new AliAppPayClient(PayErrorCodeConstants.ALI_APP, aliPayClientConfig);
    }

    @Bean
    @ConditionalOnBean(PayTypeProperties.class)
    public AliBarCodePayClient aliBarCodePayClient(){
        AliPayClientConfig aliPayClientConfig = getAliPayClientConfig();
        if (Objects.isNull(aliPayClientConfig)){
            return null;
        }
        return new AliBarCodePayClient(PayErrorCodeConstants.ALI_BAR, aliPayClientConfig);
    }

    @Bean
    @ConditionalOnBean(PayTypeProperties.class)
    public AliPcPayClient aliPcPayClient(){
        AliPayClientConfig aliPayClientConfig = getAliPayClientConfig();
        if (Objects.isNull(aliPayClientConfig)){
            return null;
        }
        return new AliPcPayClient(PayErrorCodeConstants.ALI_PC, aliPayClientConfig);
    }

    @Bean
    @ConditionalOnBean(PayTypeProperties.class)
    public AliQrCodePayClient aliQrCodePayClient(){
        AliPayClientConfig aliPayClientConfig = getAliPayClientConfig();
        if (Objects.isNull(aliPayClientConfig)){
            return null;
        }
        return new AliQrCodePayClient(PayErrorCodeConstants.ALI_QR_CODE, aliPayClientConfig);
    }

    @Bean
    @ConditionalOnBean(PayTypeProperties.class)
    public AliWapPayClient aliWapPayClient(){
        AliPayClientConfig aliPayClientConfig = getAliPayClientConfig();
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

    private AliPayClientConfig getAliPayClientConfig(){
        PayTypeProperties.zfb zfb = payTypeProperties.getZfb();
        if (ObjectUtil.isNull(zfb)){
            return null;
        }
        AliPayClientConfig aliPayClientConfig = new AliPayClientConfig();
        if (!StringUtils.hasText(zfb.getServerUrl())){
            ExceptionUtil.castServerException(ZFB_PAY_CLIENT_CONFIG_ERROR, "serverUrl");
        }
        if (!StringUtils.hasText(zfb.getAppId())){
            ExceptionUtil.castServerException(ZFB_PAY_CLIENT_CONFIG_ERROR, "appId");
        }
        if (ObjectUtil.isNull(zfb.getMode())){
            ExceptionUtil.castServerException(ZFB_PAY_CLIENT_CONFIG_ERROR, "mode");
        }
        aliPayClientConfig.setServerUrl(zfb.getServerUrl());
        aliPayClientConfig.setAppId(zfb.getAppId());
        aliPayClientConfig.setSignType(zfb.getSignType());
        if (!zfb.getMode().equals(AliPayClientConfig.MODE_PUBLIC_KEY) && !zfb.getMode().equals(AliPayClientConfig.MODE_PUBLIC_KEY_AND_CERTIFICATE)){
            ExceptionUtil.castServerException(ZFB_PAY_CLIENT_MODE_ERROR);
        }
        aliPayClientConfig.setMode(zfb.getMode());
        if (zfb.getMode().equals(AliPayClientConfig.MODE_PUBLIC_KEY)){
            if (ObjectUtil.isNull(zfb.getPublicKeyMode())){
                ExceptionUtil.castServerException(ZFB_PAY_CLIENT_CONFIG_ERROR, "publicKeyMode");
            }
            if (!StringUtils.hasText(zfb.getPublicKeyMode().getPrivateKey())){
                ExceptionUtil.castServerException(ZFB_PAY_CLIENT_CONFIG_ERROR, "privateKey");
            }
            if (!StringUtils.hasText(zfb.getPublicKeyMode().getAlipayPublicKey())){
                ExceptionUtil.castServerException(ZFB_PAY_CLIENT_CONFIG_ERROR, "alipayPublicKey");
            }
            aliPayClientConfig.setPrivateKey(zfb.getPublicKeyMode().getPrivateKey());
            aliPayClientConfig.setAlipayPublicKey(zfb.getPublicKeyMode().getAlipayPublicKey());
        }else {
            if (ObjectUtil.isNull(zfb.getCertMode())){
                ExceptionUtil.castServerException(ZFB_PAY_CLIENT_CONFIG_ERROR, "certMode");
            }
            if (!StringUtils.hasText(zfb.getCertMode().getAppCertContent())){
                ExceptionUtil.castServerException(ZFB_PAY_CLIENT_CONFIG_ERROR, "appCertContent");
            }
            if (!StringUtils.hasText(zfb.getCertMode().getAlipayPublicCertContent())){
                ExceptionUtil.castServerException(ZFB_PAY_CLIENT_CONFIG_ERROR, "alipayPublicCertContent");
            }
            if (!StringUtils.hasText(zfb.getCertMode().getRootCertContent())){
                ExceptionUtil.castServerException(ZFB_PAY_CLIENT_CONFIG_ERROR, "rootCertContent");
            }
            aliPayClientConfig.setAppCertContent(zfb.getCertMode().getAppCertContent());
            aliPayClientConfig.setAlipayPublicCertContent(zfb.getCertMode().getAlipayPublicCertContent());
            aliPayClientConfig.setRootCertContent(zfb.getCertMode().getRootCertContent());
        }
        return aliPayClientConfig;
    }

    private WxPayClientConfig getWxClientConfig(){
        PayTypeProperties.wx wx = payTypeProperties.getWx();
        if (ObjectUtil.isNull(wx)){
            return null;
        }
        WxPayClientConfig wxPayClientConfig = new WxPayClientConfig();
        if (!StringUtils.hasText(wx.getApiVersion())){
            ExceptionUtil.castServerException(WX_PAY_CLIENT_CONFIG_ERROR, "apiVersion");
        }
        if (!StringUtils.hasText(wx.getAppId())){
            ExceptionUtil.castServerException(WX_PAY_CLIENT_CONFIG_ERROR, "appId");
        }
        if (!StringUtils.hasText(wx.getMchId())){
            ExceptionUtil.castServerException(WX_PAY_CLIENT_CONFIG_ERROR, "mchId");
        }
        if (!wx.getApiVersion().equals(WxPayClientConfig.API_VERSION_V2) && !wx.getApiVersion().equals(WxPayClientConfig.API_VERSION_V3)){
            ExceptionUtil.castServerException(WX_PAY_CLIENT_API_VERSION_ERROR);
        }
        if (wx.getApiVersion().equals(WxPayClientConfig.API_VERSION_V2)){
            if (ObjectUtil.isNull(wx.getWxV2())){
                ExceptionUtil.castServerException(WX_PAY_CLIENT_CONFIG_ERROR, "wxV2");
            }
            if (!StringUtils.hasText(wx.getWxV2().getMchKey())){
                ExceptionUtil.castServerException(WX_PAY_CLIENT_CONFIG_ERROR, "mchKey");
            }
            if (!StringUtils.hasText(wx.getWxV2().getKeyContent())){
                ExceptionUtil.castServerException(WX_PAY_CLIENT_CONFIG_ERROR, "keyContent");
            }
            wxPayClientConfig.setMchKey(wx.getWxV2().getMchKey());
            wxPayClientConfig.setKeyContent(wx.getWxV2().getKeyContent());
        }else {
            if (ObjectUtil.isNull(wx.getWxV3())){
                ExceptionUtil.castServerException(WX_PAY_CLIENT_CONFIG_ERROR, "wxV3");
            }
            if (!StringUtils.hasText(wx.getWxV3().getApiV3Key())){
                ExceptionUtil.castServerException(WX_PAY_CLIENT_CONFIG_ERROR, "apiV3Key");
            }
            if (!StringUtils.hasText(wx.getWxV3().getPrivateKeyContent())){
                ExceptionUtil.castServerException(WX_PAY_CLIENT_CONFIG_ERROR, "privateKeyContent");
            }
            if (!StringUtils.hasText(wx.getWxV3().getPrivateCertContent())){
                ExceptionUtil.castServerException(WX_PAY_CLIENT_CONFIG_ERROR, "privateCertContent");
            }
            wxPayClientConfig.setApiV3Key(wx.getWxV3().getApiV3Key());
            wxPayClientConfig.setPrivateKeyContent(wx.getWxV3().getPrivateKeyContent());
            wxPayClientConfig.setPrivateCertContent(wx.getWxV3().getPrivateCertContent());
        }
        return wxPayClientConfig;
    }
}
