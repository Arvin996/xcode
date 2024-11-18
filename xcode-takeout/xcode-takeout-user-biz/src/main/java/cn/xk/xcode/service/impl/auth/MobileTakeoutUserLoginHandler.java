package cn.xk.xcode.service.impl.auth;

import cn.xk.xcode.client.CheckCodeClientApi;
import cn.xk.xcode.entity.dto.CheckCodeVerifyReqDto;
import cn.xk.xcode.entity.dto.user.UserLoginDto;
import cn.xk.xcode.entity.po.TakeoutUserPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.TakeoutRoleService;
import cn.xk.xcode.service.TakeoutUserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static cn.xk.xcode.constant.GlobalTakeoutUserConstant.MOBILE_NOT_EXISTS;
import static cn.xk.xcode.entity.def.TakeoutUserTableDef.TAKEOUT_USER_PO;

/**
 * @Author xuk
 * @Date 2024/10/30 16:29
 * @Version 1.0.0
 * @Description MobileTakeoutUserLoginHandler
 **/
@Service("mobile_loginHandler")
public class MobileTakeoutUserLoginHandler extends AbstractTakeoutUserLoginHandler {

    public MobileTakeoutUserLoginHandler(CheckCodeClientApi checkCodeClientApi, TakeoutUserService takeoutUserService, TakeoutRoleService takeoutRoleService) {
        super(checkCodeClientApi, takeoutUserService, takeoutRoleService);
    }

    @Override
    public TakeoutUserPo validateLogin(UserLoginDto userLoginDto) {
        String mobile = userLoginDto.getMobile();
        TakeoutUserPo takeoutUserPo = takeoutUserService.getOne(TAKEOUT_USER_PO.MOBILE.eq(mobile));
        if (Objects.isNull(takeoutUserPo)) {
            ExceptionUtil.castServiceException(MOBILE_NOT_EXISTS, userLoginDto.getMobile());
        }
        CommonResult<Boolean> commonResult = checkCodeClientApi.verifyCode(CheckCodeVerifyReqDto
                .builder()
                .code(userLoginDto.getCode())
                .mobile(userLoginDto.getMobile())
                .type(userLoginDto.getLoginType())
                .build());
        if (!CommonResult.isSuccess(commonResult)){
            ExceptionUtil.castServiceException0(commonResult.getCode(), commonResult.getMsg());
        }
        return takeoutUserPo;
    }
}
