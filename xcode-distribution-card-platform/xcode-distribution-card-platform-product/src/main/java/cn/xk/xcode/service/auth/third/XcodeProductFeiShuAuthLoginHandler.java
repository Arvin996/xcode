package cn.xk.xcode.service.auth.third;

import cn.xk.xcode.config.XcodeThirdLoginProperties;
import cn.xk.xcode.pojo.ThirdLoginType;
import cn.xk.xcode.service.ProductMerchantService;
import cn.xk.xcode.service.ProductMerchantThirdService;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.request.AuthFeishuRequest;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2025/6/10 10:57
 * @Version 1.0.0
 * @Description XcodeProductFeiShuAuthLoginHandler
 **/
@Component
public class XcodeProductFeiShuAuthLoginHandler extends AbstractXcodeProductAuthLoginHandler {

    public XcodeProductFeiShuAuthLoginHandler(XcodeThirdLoginProperties xcodeThirdLoginProperties, ProductMerchantThirdService productMerchantThirdService, ProductMerchantService productMerchantService) {
        super(xcodeThirdLoginProperties, productMerchantThirdService, productMerchantService);
    }

    @Override
    protected ThirdLoginType getThirdLoginType() {
        return ThirdLoginType.FEISHU;
    }

    @Override
    protected AuthRequest getAuthRequest() {
        XcodeThirdLoginProperties.CommonProperties feiShu = xcodeThirdLoginProperties.getFeiShu();
        return new AuthFeishuRequest(AuthConfig.builder()
                .clientId(feiShu.getClientId())
                .clientSecret(feiShu.getClientSecret())
                .redirectUri(feiShu.getCallbackUrl())
                .build());
    }
}
