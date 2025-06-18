package cn.xk.xcode.service.auth.third;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.xk.xcode.config.XcodeThirdLoginProperties;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.core.StpMemberUtil;
import cn.xk.xcode.entity.po.ProductMerchantPo;
import cn.xk.xcode.entity.po.ProductMerchantThirdPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.handler.third.AbstractThirdLoginHandler;
import cn.xk.xcode.handler.third.ThirdLoginVo;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.ProductMerchantService;
import cn.xk.xcode.service.ProductMerchantThirdService;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static cn.xk.xcode.config.DistributionCardProductErrorCode.LOGIN_ERROR_THEN_REGISTER;
import static cn.xk.xcode.config.DistributionCardProductErrorCode.MERCHANT_IS_DISABLED;
import static cn.xk.xcode.entity.def.ProductMerchantThirdTableDef.PRODUCT_MERCHANT_THIRD_PO;

/**
 * @Author xuk
 * @Date 2025/6/10 10:13
 * @Version 1.0.0
 * @Description FeiShuAuthLoginHandler
 **/
public abstract class AbstractXcodeProductAuthLoginHandler extends AbstractThirdLoginHandler {

    private final ProductMerchantThirdService productMerchantThirdService;

    private final ProductMerchantService productMerchantService;

    public AbstractXcodeProductAuthLoginHandler(XcodeThirdLoginProperties xcodeThirdLoginProperties,
                                                ProductMerchantThirdService productMerchantThirdService,
                                                ProductMerchantService productMerchantService) {
        super(xcodeThirdLoginProperties);
        this.productMerchantThirdService = productMerchantThirdService;
        this.productMerchantService = productMerchantService;
    }

    @Override
    public ThirdLoginVo doLogin(AuthResponse<AuthUser> response) {
        ThirdLoginVo thirdLoginVo = new ThirdLoginVo();
        AuthUser authUser = response.getData();
        String uuid = authUser.getUuid();
        thirdLoginVo.setUnionId(uuid);
        thirdLoginVo.setThirdLoginType(getThirdLoginType().getType());
        ProductMerchantThirdPo productMerchantThirdPo = productMerchantThirdService.getOne(PRODUCT_MERCHANT_THIRD_PO.UNION_ID.eq(uuid));
        if (Objects.isNull(productMerchantThirdPo)) {
            thirdLoginVo.setFirstLogin(true);
            productMerchantThirdPo = new ProductMerchantThirdPo();
            productMerchantThirdPo.setUnionId(uuid);
            productMerchantThirdPo.setType(getThirdLoginType().getType());
            productMerchantThirdService.save(productMerchantThirdPo);
        } else {
            if (Objects.isNull(productMerchantThirdPo.getMerchantId())) {
                thirdLoginVo.setFirstLogin(true);
            } else {
                thirdLoginVo.setFirstLogin(false);
                ProductMerchantPo productMerchantPo = productMerchantService.getById(productMerchantThirdPo.getMerchantId());
                if (Objects.isNull(productMerchantPo)) {
                    ExceptionUtil.castServerException(LOGIN_ERROR_THEN_REGISTER);
                }
                if (CommonStatusEnum.isDisable(productMerchantPo.getStatus())) {
                    ExceptionUtil.castServerException(MERCHANT_IS_DISABLED);
                }
                SaLoginModel saLoginModel = bulidSaLoginModel(productMerchantPo);
                LoginVO loginVO = createLoginVO(productMerchantPo);
                LoginUser loginUser = new LoginUser();
                loginUser.setUsername(productMerchantPo.getUsername());
                SaTokenLoginUtils.doLogin(loginUser, saLoginModel);
                thirdLoginVo.setLoginVO(loginVO);
            }
        }
        return thirdLoginVo;
    }

    private LoginVO createLoginVO(Object userInfo) {
        LoginVO loginVo = new LoginVO();
        loginVo.setAccessToken(StpMemberUtil.getTokenValue());
        loginVo.setExpireIn(StpMemberUtil.getTokenTimeout());
        loginVo.setUserInfo(userInfo);
        return loginVo;
    }

    private SaLoginModel bulidSaLoginModel(ProductMerchantPo productMerchantPo) {
        SaLoginModel saLoginModel = SaLoginModel.create();
        Map<String, Object> extraData = new HashMap<>();
        extraData.put("clientId", "xcode-dc-product");
        extraData.put("loginId", productMerchantPo.getUsername());
        extraData.put("loginType", getThirdLoginType().getType());
        saLoginModel.setExtraData(extraData);
        return saLoginModel;
    }

}
