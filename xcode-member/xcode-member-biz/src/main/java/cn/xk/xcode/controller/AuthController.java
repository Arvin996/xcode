package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.xk.xcode.entity.dto.user.MemberUserBaseDto;
import cn.xk.xcode.entity.dto.user.MemberUserChangePasswordDto;
import cn.xk.xcode.entity.dto.user.MemberUserLoginDto;
import cn.xk.xcode.entity.dto.user.MemberUserRegisterDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.MemberUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author Administrator
 * @Date 2024/8/26 18:44
 * @Version V1.0.0
 * @Description AuthController
 */
@Tag(name = "用户会员认证接口")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private MemberUserService memberUserService;

    @SaIgnore
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public CommonResult<Boolean> userRegister(@Validated @RequestBody MemberUserRegisterDto memberUserRegisterDto){
        return CommonResult.success(memberUserService.userRegister(memberUserRegisterDto));
    }

    @SaIgnore
    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public CommonResult<LoginVO> userLogin(@Validated @RequestBody MemberUserLoginDto memberUserLoginDto){
        return CommonResult.success(memberUserService.userLogin(memberUserLoginDto));
    }

    @Operation(summary = "用户退出登录")
    @PostMapping("/logout")
    public CommonResult<Boolean> userLogout(){
        StpUtil.logout();
        return CommonResult.success(true);
    }

    @Operation(summary = "踢出用户")
    @PostMapping("/kickout")
    public CommonResult<Boolean> kickout(@RequestBody @Validated MemberUserBaseDto memberUserBaseDto){
        return CommonResult.success(memberUserService.kickout(memberUserBaseDto));
    }

    @Operation(summary = "修改密码")
    @PostMapping("/changePassword")
    public CommonResult<Boolean> changePassword(@RequestBody @Validated MemberUserChangePasswordDto memberUserChangePasswordDto){
        return CommonResult.success(memberUserService.changePassword(memberUserChangePasswordDto));
    }
}
