package cn.xk.xcode.service.auth.third;

import cn.xk.xcode.config.XcodeThirdLoginProperties;
import cn.xk.xcode.pojo.ThirdLoginType;
import cn.xk.xcode.service.ProductMerchantService;
import cn.xk.xcode.service.ProductMerchantThirdService;
import com.xkcoding.http.config.HttpConfig;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.scope.AuthGithubScope;
import me.zhyd.oauth.request.AuthGithubRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthScopeUtils;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Collections;

/**
 * @Author xuk
 * @Date 2025/6/10 14:19
 * @Version 1.0.0
 * @Description XcodeProductGithubAuthLoginHandler
 **/
@Component
public class XcodeProductGithubAuthLoginHandler extends AbstractXcodeProductAuthLoginHandler {

    public XcodeProductGithubAuthLoginHandler(XcodeThirdLoginProperties xcodeThirdLoginProperties, ProductMerchantThirdService productMerchantThirdService, ProductMerchantService productMerchantService) {
        super(xcodeThirdLoginProperties, productMerchantThirdService, productMerchantService);
    }

    @Override
    protected ThirdLoginType getThirdLoginType() {
        return ThirdLoginType.GITHUB;
    }

    @Override
    protected AuthRequest getAuthRequest() {
        XcodeThirdLoginProperties.CommonProperties github = xcodeThirdLoginProperties.getGithub();
        return new AuthGithubRequest(AuthConfig.builder()
                .clientId(github.getClientId())
                .clientSecret(github.getClientSecret())
                .scopes(AuthScopeUtils.getScopes(AuthGithubScope.USER_EMAIL, AuthGithubScope.READ_USER))
                .redirectUri(github.getCallbackUrl())
                .httpConfig(HttpConfig.builder()
                        .timeout(15000)
                        // host 和 port 请修改为开发环境的参数
                        .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10808)))
                        .build())
                .build());
    }
}
