package cn.xk.xcode.controller;

import cn.xk.xcode.entity.dto.user.UpdatePasswordDto;
import cn.xk.xcode.entity.dto.user.UpdateUserDto;
import cn.xk.xcode.entity.po.SystemUserPo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.SystemUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/5/9 10:49
 * @Version 1.0.0
 * @Description UserController
 **/
@RestController
@RequestMapping("/manage/user")
@Tag(name = "用户接口")
@Validated
public class UserController {

    @Resource
    private SystemUserService systemUserService;

    @Operation(summary = "更新用户基本信息")
    @PostMapping("/updateUserInfo")
    public CommonResult<Boolean> updateUserInfo(@RequestBody UpdateUserDto updateUserDto) {
        return CommonResult.success(systemUserService.updateUserInfo(updateUserDto));
    }

    @Operation(summary = "更新用户密码")
    @PostMapping("/updateUserPassword")
    public CommonResult<Boolean> updateUserPassword(@RequestBody UpdatePasswordDto updatePasswordDto) {
        return CommonResult.success(systemUserService.updateUserPassword(updatePasswordDto));
    }

    @Operation(summary = "更新用户头像")
    @PostMapping("/updateUserAvatar")
    public CommonResult<Boolean> updateUserAvatar(@RequestPart("file") MultipartFile file,
                                                  @RequestParam("username") String username) {
        return CommonResult.success(true);
    }

    @Operation(summary = "获取用户信息")
    @GetMapping("/getUserInfo")
    public CommonResult<SystemUserPo> getUserInfo(){
        return CommonResult.success(systemUserService.getUserInfo());
    }
}
