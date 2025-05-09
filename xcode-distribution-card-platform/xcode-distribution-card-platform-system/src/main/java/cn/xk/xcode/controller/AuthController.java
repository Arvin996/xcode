package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.xk.xcode.entity.dto.user.LoginUserDto;
import cn.xk.xcode.entity.dto.user.RegisterUserDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/5/9 10:49
 * @Version 1.0.0
 * @Description AuthController
 **/
@RequestMapping("/manage/auth")
@RestController
@Tag(name = "AuthController", description = "授权接口")
@Validated
public class AuthController {

    @Resource
    private AuthService authService;

    @SaIgnore
    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public CommonResult<Boolean> register(@RequestBody RegisterUserDto registerUserDto) {
        return CommonResult.success(authService.register(registerUserDto));
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public CommonResult<?> login(@RequestBody LoginUserDto loginUserDto) {
        return null;
    }

}
