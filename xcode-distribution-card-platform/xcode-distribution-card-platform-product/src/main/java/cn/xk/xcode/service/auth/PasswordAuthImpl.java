package cn.xk.xcode.service.auth;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.xk.xcode.core.StpMemberUtil;
import cn.xk.xcode.entity.po.ProductMerchantPo;
import cn.xk.xcode.exception.core.AssertUtil;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.AbstractLoginHandler;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.ProductMerchantService;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.xk.xcode.config.DistributionCardProductErrorCode.MERCHANT_USERNAME_OR_PASSWORD_ERROR;
import static cn.xk.xcode.entity.def.ProductMerchantTableDef.PRODUCT_MERCHANT_PO;

@Service
public class PasswordAuthImpl extends AbstractLoginHandler {

    @Resource
    private ProductMerchantService productMerchantService;

    @Override
    public LoginVO handlerLogin(LoginInfoDto loginInfoDto) {
        String username = loginInfoDto.getUsername();
        String password = loginInfoDto.getPassword();
        ProductMerchantPo productMerchantPo = productMerchantService.getOne(PRODUCT_MERCHANT_PO.USERNAME.eq(username));
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
        return "password";
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