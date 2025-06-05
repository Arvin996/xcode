package cn.xk.xcode.service.auth;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.StpMemberUtil;
import cn.xk.xcode.entity.po.ProductMerchantPo;
import cn.xk.xcode.exception.core.AssertUtil;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.AbstractLoginHandler;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.ProductMerchantService;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.xk.xcode.config.DistributionCardProductErrorCode.MERCHANT_LOGIN_EMAIL_MUST_NOT_EMPTY;
import static cn.xk.xcode.config.DistributionCardProductErrorCode.MERCHANT_USERNAME_OR_PASSWORD_ERROR;
import static cn.xk.xcode.entity.def.ProductMerchantTableDef.PRODUCT_MERCHANT_PO;

/**
 * @Author xuk
 * @Date 2025/5/26 13:53
 * @Version 1.0.0
 * @Description EmailAuthImpl
 **/
@Service
public class EmailAuthImpl extends AbstractLoginHandler {

    @Resource
    private ProductMerchantService productMerchantService;

    @Override
    public LoginVO handlerLogin(LoginInfoDto loginInfoDto) {
        String email = loginInfoDto.getEmail();
        String password = loginInfoDto.getPassword();
        if (StrUtil.isBlank(email)) {
            ExceptionUtil.castServerException(MERCHANT_LOGIN_EMAIL_MUST_NOT_EMPTY);
        }
        ProductMerchantPo productMerchantPo = productMerchantService.getOne(PRODUCT_MERCHANT_PO.EMAIL.eq(email));
        AssertUtil.assertNullCastServiceEx(productMerchantPo, MERCHANT_USERNAME_OR_PASSWORD_ERROR);
        if (!password.equals(productMerchantPo.getPassword())) {
            throw new ServiceException(MERCHANT_USERNAME_OR_PASSWORD_ERROR);
        }
        SaLoginModel saLoginModel = bulidSaLoginModel("xcode-dc-product", loginInfoDto);
        LoginVO loginVO = createLoginVO(productMerchantPo);
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(productMerchantPo.getUsername());
        SaTokenLoginUtils.doLogin(loginUser, saLoginModel);
        return loginVO;
    }

    @Override
    public Object validateClient(LoginInfoDto loginInfoDto) {
        return null;
    }

    @Override
    public String loginType() {
        return "email";
    }

    @Override
    public LoginVO createLoginVO(Object userInfo) {
        LoginVO loginVo = new LoginVO();
        loginVo.setAccessToken(StpMemberUtil.getTokenValue());
        loginVo.setExpireIn(StpMemberUtil.getTokenTimeout());
        loginVo.setUserInfo(userInfo);
        return loginVo;
    }
}
