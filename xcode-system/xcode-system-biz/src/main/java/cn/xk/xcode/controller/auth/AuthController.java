package cn.xk.xcode.controller.auth;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.xk.xcode.config.LoginValidationGroupsConfig;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginVO;
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
 * @author xukai
 * @version 1.0
 * @date 2024/6/22 16:18
 * @description
 */
@RestController
@Tag(name = "登录认证接口")
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private AuthService authService;

    // 登录接口
    @Operation(summary = "用户密码登录认证接口")
    @PostMapping("/passwordLogin")
    public CommonResult<LoginVO> passwordLogin(@Validated({LoginValidationGroupsConfig.PASSWORD_LOGIN.class}) @RequestBody LoginInfoDto loginUserDto) {
        return authService.doLogin(loginUserDto);
    }

    // 登录接口
    @Operation(summary = "微信登录认证接口")
    @PostMapping("/wxLogin")
    public CommonResult<LoginVO> wxLogin(@Validated({LoginValidationGroupsConfig.WX_LOGIN.class}) @RequestBody LoginInfoDto loginUserDto) {
        return authService.doLogin(loginUserDto);
    }

    @Operation(summary = "qq登录认证接口")
    @PostMapping("/qqLogin")
    public CommonResult<LoginVO> qqLogin(@Validated({LoginValidationGroupsConfig.WX_LOGIN.class}) @RequestBody LoginInfoDto loginUserDto) {
        return authService.doLogin(loginUserDto);
    }


    // 下线
    @Operation(summary = "下线接口")
    @PostMapping("/logout")
    public CommonResult<Boolean> logout() {
        return authService.logout();
    }

    // 踢人下线
    @Operation(summary = "踢人下线接口")
    @SaCheckRole("admin")
    @PostMapping("/kickout")
    public CommonResult<Boolean> kickout(@RequestBody LoginInfoDto loginInfoDto) {
        return authService.kickout(loginInfoDto);
    }

    //todo 注册 找回密码
}
