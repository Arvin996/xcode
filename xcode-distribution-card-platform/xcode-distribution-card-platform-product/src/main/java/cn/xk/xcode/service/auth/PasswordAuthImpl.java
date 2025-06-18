package cn.xk.xcode.service.auth;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.StpMemberUtil;
import cn.xk.xcode.entity.po.ProductMerchantPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.AbstractLoginHandler;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.ProductMerchantService;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import static cn.xk.xcode.config.DistributionCardProductErrorCode.*;
import static cn.xk.xcode.entity.def.ProductMerchantTableDef.PRODUCT_MERCHANT_PO;

@Service
public class PasswordAuthImpl extends AbstractLoginHandler {

    @Resource
    private ProductMerchantService productMerchantService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public LoginVO handlerLogin(LoginInfoDto loginInfoDto) {
        String username = loginInfoDto.getUsername();
        String password = loginInfoDto.getPassword();
        ProductMerchantPo productMerchantPo = productMerchantService.getOne(PRODUCT_MERCHANT_PO.USERNAME.eq(username));
        if (ObjectUtil.isNull(productMerchantPo)) {
            ExceptionUtil.castServiceException(MERCHANT_USERNAME_OR_PASSWORD_ERROR);
        }
        if (stringRedisTemplate.hasKey(LOGIN_LOCKED_USER_USERNAME_KEY_PREFIX + username)) {
            ExceptionUtil.castServerException(LOGIN_ERROR_COUNT_FIVE_DISABLE);
        }
        if (!password.equals(productMerchantPo.getPassword())) {
            // 判断是否错误了3次
            String count = stringRedisTemplate.opsForValue().get(USER_LOGIN_ERROR_PREFIX + username);
            if (StrUtil.isBlank(count)) {
                stringRedisTemplate.opsForValue().set(USER_LOGIN_ERROR_PREFIX + username, "1", ERROR_COUNT_EXPIRE_TIME, TimeUnit.SECONDS);
            } else {
                int errorCount = Integer.parseInt(count);
                errorCount++;
                if (errorCount > MAX_ERROR_LOGIN_COUNT) {
                    ExceptionUtil.castServiceException(LOGIN_ERROR_COUNT_FIVE_DISABLE);
                }
                stringRedisTemplate.opsForValue().set(USER_LOGIN_ERROR_PREFIX + username, String.valueOf(errorCount), ERROR_COUNT_EXPIRE_TIME, TimeUnit.SECONDS);
                if (errorCount >= MAX_ERROR_TO_CAPTCHA && errorCount < MAX_ERROR_LOGIN_COUNT) {
                    return LoginVO.builder().requireCaptcha(true).build();
                }
                if (errorCount == MAX_ERROR_LOGIN_COUNT) {
                    stringRedisTemplate.opsForValue().set(LOGIN_LOCKED_USER_USERNAME_KEY_PREFIX + username, username, LOCK_DISABLED_TIME, TimeUnit.SECONDS);
                }
            }
            throw new ServiceException(MERCHANT_USERNAME_OR_PASSWORD_ERROR);
        }
        stringRedisTemplate.delete(USER_LOGIN_ERROR_PREFIX + username);
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
        return "username";
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