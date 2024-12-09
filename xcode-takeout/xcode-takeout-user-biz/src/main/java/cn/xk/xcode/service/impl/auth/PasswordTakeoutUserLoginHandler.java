package cn.xk.xcode.service.impl.auth;

import cn.xk.xcode.client.CaptchaClientApi;
import cn.xk.xcode.entity.dto.user.UserLoginDto;
import cn.xk.xcode.entity.po.TakeoutUserPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.TakeoutRoleService;
import cn.xk.xcode.service.TakeoutUserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static cn.xk.xcode.constant.GlobalTakeoutUserConstant.ACCOUNT_OR_PASSWORD_ERROR;
import static cn.xk.xcode.constant.GlobalTakeoutUserConstant.CONFIRM_PASSWORD_NOT_EQUAL;
import static cn.xk.xcode.entity.def.TakeoutUserTableDef.TAKEOUT_USER_PO;

/**
 * @Author xuk
 * @Date 2024/10/30 16:24
 * @Version 1.0.0
 * @Description PasswordTakeoutUserLoginHandler
 **/
@Service("password_loginHandler")
public class PasswordTakeoutUserLoginHandler extends AbstractTakeoutUserLoginHandler{

    public PasswordTakeoutUserLoginHandler(CaptchaClientApi captchaClientApi, TakeoutUserService takeoutUserService, TakeoutRoleService takeoutRoleService) {
        super(captchaClientApi, takeoutUserService, takeoutRoleService);
    }

    @Override
    public TakeoutUserPo validateLogin(UserLoginDto userLoginDto) {
        String mobile = userLoginDto.getMobile();
        String password = userLoginDto.getPassword();
        TakeoutUserPo takeoutUserPo = takeoutUserService.getOne(TAKEOUT_USER_PO.MOBILE.eq(mobile));
        if (Objects.isNull(takeoutUserPo)){
            ExceptionUtil.castServiceException(ACCOUNT_OR_PASSWORD_ERROR);
        }
        if (!password.equals(takeoutUserPo.getPassword())){
            ExceptionUtil.castServiceException(CONFIRM_PASSWORD_NOT_EQUAL);
        }
        return takeoutUserPo;
    }
}
