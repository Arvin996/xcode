package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.user.RegisterUserDto;

/**
 * @Author xuk
 * @Date 2025/5/9 10:56
 * @Version 1.0.0
 * @Description AuthService
 **/
public interface AuthService {
    Boolean register(RegisterUserDto registerUserDto);
}
