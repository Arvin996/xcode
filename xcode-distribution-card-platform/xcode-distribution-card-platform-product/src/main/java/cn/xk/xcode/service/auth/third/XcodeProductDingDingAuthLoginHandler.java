package cn.xk.xcode.service.auth.third;

import cn.xk.xcode.config.XcodeThirdLoginProperties;
import cn.xk.xcode.pojo.ThirdLoginType;
import cn.xk.xcode.service.ProductMerchantService;
import cn.xk.xcode.service.ProductMerchantThirdService;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthDingTalkV2Request;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2025/6/10 14:14
 * @Version 1.0.0
 * @Description XcodeProductDingDingAuthLoginHandler
 **/
@Component
public class XcodeProductDingDingAuthLoginHandler extends AbstractXcodeProductAuthLoginHandler {

    public XcodeProductDingDingAuthLoginHandler(XcodeThirdLoginProperties xcodeThirdLoginProperties, ProductMerchantThirdService productMerchantThirdService, ProductMerchantService productMerchantService) {
        super(xcodeThirdLoginProperties, productMerchantThirdService, productMerchantService);
    }

    @Override
    protected ThirdLoginType getThirdLoginType() {
        return ThirdLoginType.DINGDING;
    }

    @Override
    protected AuthRequest getAuthRequest() {
        XcodeThirdLoginProperties.CommonProperties dingDing = xcodeThirdLoginProperties.getDingDing();
        return new AuthDingTalkV2Request(AuthConfig.builder()
                .clientId(dingDing.getClientId())
                .clientSecret(dingDing.getClientSecret())
                .redirectUri(dingDing.getCallbackUrl())
                .build());
    }
}
