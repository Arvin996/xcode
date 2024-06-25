package cn.xk.xcode.service;

import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginVO;

/**
 * @Author xuk
 * @Date 2024/6/24 14:16
 * @Version 1.0
 * @Description AuthService
 */
public interface AuthService {
    CommonResult<LoginVO> doLogin(LoginInfoDto loginUserDto);

    CommonResult<Boolean> logout();

    CommonResult<Boolean> kickout(LoginInfoDto loginInfoDto);
}
