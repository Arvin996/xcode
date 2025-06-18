package cn.xk.xcode.service.auth.third;

import cn.xk.xcode.config.XcodeThirdLoginProperties;
import cn.xk.xcode.pojo.ThirdLoginType;
import cn.xk.xcode.service.ProductMerchantService;
import cn.xk.xcode.service.ProductMerchantThirdService;
import com.xkcoding.http.config.HttpConfig;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthGoogleRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @Author xuk
 * @Date 2025/6/13 11:40
 * @Version 1.0.0
 * @Description XcodeProductGoogleAuthLoginHandler
 **/
@Component
public class XcodeProductGoogleAuthLoginHandler extends AbstractXcodeProductAuthLoginHandler {

    public XcodeProductGoogleAuthLoginHandler(XcodeThirdLoginProperties xcodeThirdLoginProperties, ProductMerchantThirdService productMerchantThirdService, ProductMerchantService productMerchantService) {
        super(xcodeThirdLoginProperties, productMerchantThirdService, productMerchantService);
    }

    @Override
    protected ThirdLoginType getThirdLoginType() {
        return ThirdLoginType.GOOGLE;
    }

    @Override
    protected AuthRequest getAuthRequest() {
        XcodeThirdLoginProperties.CommonProperties google = xcodeThirdLoginProperties.getGoogle();
        return new AuthGoogleRequest(AuthConfig.builder()
                .clientId(google.getClientId())
                .clientSecret(google.getClientSecret())
                .redirectUri(google.getCallbackUrl())
                // 针对国外平台配置代理
                .httpConfig(HttpConfig.builder()
                        .timeout(15000)
                        // host 和 port 请修改为开发环境的参数
                        .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10080)))
                        .build())
                .build());
    }
}
