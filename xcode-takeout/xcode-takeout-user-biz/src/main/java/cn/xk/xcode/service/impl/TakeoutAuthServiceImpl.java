package cn.xk.xcode.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.xk.xcode.client.CheckCodeClientApi;
import cn.xk.xcode.entity.dto.CheckCodeVerifyReqDto;
import cn.xk.xcode.entity.dto.user.RegisterUserDto;
import cn.xk.xcode.entity.dto.user.UserLoginDto;
import cn.xk.xcode.entity.po.TakeoutUserPo;
import cn.xk.xcode.enums.CheckCodeGenerateType;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.TakeoutAuthService;
import cn.xk.xcode.service.TakeoutRoleService;
import cn.xk.xcode.service.TakeoutUserService;
import cn.xk.xcode.service.impl.auth.AbstractTakeoutUserLoginHandler;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.xk.xcode.constant.GlobalTakeoutUserConstant.*;
import static cn.xk.xcode.entity.def.TakeoutRoleTableDef.TAKEOUT_ROLE_PO;
import static cn.xk.xcode.entity.def.TakeoutUserTableDef.TAKEOUT_USER_PO;

/**
 * @Author xuk
 * @Date 2024/10/30 15:23
 * @Version 1.0.0
 * @Description TakeoutAuthServiceImpl
 **/
@Service
public class TakeoutAuthServiceImpl implements TakeoutAuthService {

    @Resource
    private TakeoutUserService takeoutUserService;

    @Resource
    private CheckCodeClientApi checkCodeClientApi;

    @Resource
    private TakeoutRoleService takeoutRoleService;

    @Override
    public Boolean registerUser(RegisterUserDto registerUserDto) {
        String password = registerUserDto.getPassword();
        String confirmPassword = registerUserDto.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            ExceptionUtil.castServiceException(CONFIRM_PASSWORD_NOT_EQUAL);
        }
        TakeoutUserPo takeoutUserPo = takeoutUserService.getOne(TAKEOUT_USER_PO.MOBILE.eq(registerUserDto.getMobile()));
        if (ObjUtil.isNotNull(takeoutUserPo)) {
            ExceptionUtil.castServiceException0(MOBILE_HAS_USED, registerUserDto.getMobile());
        }
        CommonResult<Boolean> commonResult = checkCodeClientApi.verifyCode(CheckCodeVerifyReqDto
                .builder()
                .code(registerUserDto.getCode())
                .mobile(registerUserDto.getMobile())
                .type(CheckCodeGenerateType.MOBILE.getCode())
                .build());
        if (!CommonResult.isSuccess(commonResult)){
            ExceptionUtil.castServiceException0(commonResult.getCode(), commonResult.getMsg());
        }
        if (!takeoutRoleService.exists(TAKEOUT_ROLE_PO.ID.eq(registerUserDto.getRoleId()))){
            ExceptionUtil.castServiceException(ROLE_NOT_EXISTS);
        }
        TakeoutUserPo userPo = new TakeoutUserPo();
        userPo.setName(registerUserDto.getName());
        userPo.setRoleId(registerUserDto.getRoleId());
        userPo.setMobile(registerUserDto.getMobile());
        userPo.setPassword(registerUserDto.getPassword());
        userPo.setSex(registerUserDto.getSex());
        userPo.setIdNumber(registerUserDto.getIdNumber());
        userPo.setAvatar(registerUserDto.getAvatar());
        return takeoutUserService.save(userPo);
    }

    @Override
    public LoginVO userLogin(UserLoginDto userLoginDto) {
        AbstractTakeoutUserLoginHandler loginHandler = SpringUtil.getBean(userLoginDto.getLoginType() + "_loginHandler", AbstractTakeoutUserLoginHandler.class);
        return loginHandler.doLogin(userLoginDto);
    }
}
