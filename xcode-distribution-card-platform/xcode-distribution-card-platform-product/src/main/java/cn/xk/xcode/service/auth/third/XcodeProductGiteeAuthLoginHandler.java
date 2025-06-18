package cn.xk.xcode.service.auth.third;

import cn.xk.xcode.config.XcodeThirdLoginProperties;
import cn.xk.xcode.pojo.ThirdLoginType;
import cn.xk.xcode.service.ProductMerchantService;
import cn.xk.xcode.service.ProductMerchantThirdService;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthGiteeRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2025/6/10 14:17
 * @Version 1.0.0
 * @Description XcodeProductGiteeAuthLoginHandler
 **/
@Component
public class XcodeProductGiteeAuthLoginHandler extends AbstractXcodeProductAuthLoginHandler {

    public XcodeProductGiteeAuthLoginHandler(XcodeThirdLoginProperties xcodeThirdLoginProperties, ProductMerchantThirdService productMerchantThirdService, ProductMerchantService productMerchantService) {
        super(xcodeThirdLoginProperties, productMerchantThirdService, productMerchantService);
    }

    @Override
    protected ThirdLoginType getThirdLoginType() {
        return ThirdLoginType.GITEE;
    }

    @Override
    protected AuthRequest getAuthRequest() {
        XcodeThirdLoginProperties.CommonProperties gitee = xcodeThirdLoginProperties.getGitee();
        return new AuthGiteeRequest(AuthConfig.builder()
                .clientId(gitee.getClientId())
                .clientSecret(gitee.getClientSecret())
                .redirectUri(gitee.getCallbackUrl())
                .build());

    }
}
