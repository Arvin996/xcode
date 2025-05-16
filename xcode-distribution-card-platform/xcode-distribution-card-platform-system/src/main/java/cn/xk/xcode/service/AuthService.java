package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.user.LoginUserDto;
import cn.xk.xcode.entity.dto.user.RegisterUserDto;
import cn.xk.xcode.pojo.LoginVO;

/**
 * @Author xuk
 * @Date 2025/5/9 10:56
 * @Version 1.0.0
 * @Description AuthService
 **/
public interface AuthService {
    Boolean register(RegisterUserDto registerUserDto);

    LoginVO Login(LoginUserDto loginUserDto);

    Boolean logout();

}
