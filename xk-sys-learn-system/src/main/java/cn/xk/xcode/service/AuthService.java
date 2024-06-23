package cn.xk.xcode.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.xk.xcode.entity.dto.user.LoginUserDto;
import cn.xk.xcode.pojo.CommonResult;

import java.util.Map;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/22 16:21
 * @description
 */
public interface AuthService {

    CommonResult<SaTokenInfo> doLogin(LoginUserDto loginUserDto);
}
