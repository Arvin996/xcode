package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.xk.xcode.entity.dto.user.LoginUserDto;
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
import java.util.Map;

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
    @Operation(summary = "登录认证接口")
    @PostMapping("/doLogin")
    public CommonResult<SaTokenInfo> doLogin(@Validated @RequestBody LoginUserDto loginUserDto) {
        return authService.doLogin(loginUserDto);
    }

    // 下线
    @Operation(summary = "下线接口")
    @PostMapping("/logout")
    public CommonResult<Boolean> logout(@RequestBody Map<String, String> map) {
        StpUtil.login(map.get("username"));
        return CommonResult.success(true);
    }

    // 踢人下线
    @Operation(summary = "踢人下线接口")
    @SaCheckRole("admin")
    @PostMapping("/kickout")
    public CommonResult<Boolean> kickout(@RequestBody Map<String, String> map) {
        StpUtil.kickout(map.get("username"));
        return CommonResult.success(true);
    }
}
