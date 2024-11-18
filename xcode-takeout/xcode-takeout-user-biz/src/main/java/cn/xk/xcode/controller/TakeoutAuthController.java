package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.xk.xcode.entity.dto.user.RegisterUserDto;
import cn.xk.xcode.entity.dto.user.UserLoginDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.TakeoutAuthService;
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
 * @Date 2024/10/30 15:07
 * @Version 1.0.0
 * @Description TakeoutAuthController
 **/
@RestController
@RequestMapping("/takeout/auth")
@Tag(name = "外卖用户用户认证接口")
public class TakeoutAuthController {

    @Resource
    private TakeoutAuthService takeoutAuthService;

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    @SaIgnore
    public CommonResult<Boolean> registerUser(@Validated @RequestBody RegisterUserDto registerUserDto) {
        return CommonResult.success(takeoutAuthService.registerUser(registerUserDto));
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    @SaIgnore
    public CommonResult<LoginVO> userLogin(@Validated @RequestBody UserLoginDto userLoginDto){
        return CommonResult.success(takeoutAuthService.userLogin(userLoginDto));
    }
}
