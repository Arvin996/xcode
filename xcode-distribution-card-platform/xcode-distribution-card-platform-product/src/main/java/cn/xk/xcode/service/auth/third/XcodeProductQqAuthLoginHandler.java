package cn.xk.xcode.service.auth.third;

import cn.xk.xcode.config.XcodeThirdLoginProperties;
import cn.xk.xcode.pojo.ThirdLoginType;
import cn.xk.xcode.service.ProductMerchantService;
import cn.xk.xcode.service.ProductMerchantThirdService;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthQqRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2025/6/10 15:17
 * @Version 1.0.0
 * @Description XcodeProductQqAuthLoginHandler
 **/
@Component
public class XcodeProductQqAuthLoginHandler extends AbstractXcodeProductAuthLoginHandler {

    public XcodeProductQqAuthLoginHandler(XcodeThirdLoginProperties xcodeThirdLoginProperties, ProductMerchantThirdService productMerchantThirdService, ProductMerchantService productMerchantService) {
        super(xcodeThirdLoginProperties, productMerchantThirdService, productMerchantService);
    }

    @Override
    protected ThirdLoginType getThirdLoginType() {
        return ThirdLoginType.QQ;
    }

    @Override
    protected AuthRequest getAuthRequest() {
        XcodeThirdLoginProperties.QQProperties qq = xcodeThirdLoginProperties.getQq();
        return new AuthQqRequest(AuthConfig.builder()
                .clientId(qq.getAppId())
                .clientSecret(qq.getAppKey())
                .redirectUri(qq.getCallbackUrl())
                .build());
    }
}
