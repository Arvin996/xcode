package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.user.RegisterUserDto;
import cn.xk.xcode.entity.dto.user.UserLoginDto;
import cn.xk.xcode.pojo.LoginVO;

/**
 * @Author xuk
 * @Date 2024/10/30 15:22
 * @Version 1.0.0
 * @Description TakeoutAuthService
 **/
public interface TakeoutAuthService {
    Boolean registerUser(RegisterUserDto registerUserDto);

    LoginVO userLogin(UserLoginDto userLoginDto);
}
