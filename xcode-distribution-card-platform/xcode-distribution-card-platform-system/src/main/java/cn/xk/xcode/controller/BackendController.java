package cn.xk.xcode.controller;

import cn.xk.xcode.annotation.DictTrans;
import cn.xk.xcode.annotation.SaMemberCheckRole;
import cn.xk.xcode.core.annotation.AutoFlexTrans;
import cn.xk.xcode.entity.dto.user.QueryUserDto;
import cn.xk.xcode.entity.dto.user.UpdateUserRoleDto;
import cn.xk.xcode.entity.vo.user.SystemUserVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.backend.BackendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2025/5/13 9:05
 * @Version 1.0.0
 * @Description BackendController
 **/
@RestController
@RequestMapping("/manage/backend")
@Tag(name = "BackendController", description = "BackendController 后端超级用户接口")
@SaMemberCheckRole("root")
@Validated
public class BackendController {

    @Resource
    private BackendService backendService;

    @Operation(summary = "锁定用户")
    @GetMapping("/lockUser/{username}")
    public CommonResult<Boolean> lockUser(@PathVariable("username") String username) {
        return CommonResult.success(backendService.lockUser(username));
    }

    @AutoFlexTrans
    @Operation(summary = "获取所有用户信息")
    @PostMapping("/queryAllUsers")
    @DictTrans
    private CommonResult<PageResult<SystemUserVo>> queryAllUsers(@RequestBody QueryUserDto queryUserDto) {
        return CommonResult.success(backendService.queryAllUsers(queryUserDto));
    }

    @Operation(summary = "解锁用户")
    @GetMapping("/unlockUser/{username}")
    public CommonResult<Boolean> unlockUser(@PathVariable("username") String username) {
        return CommonResult.success(backendService.unlockUser(username));
    }

    @PostMapping("/kickout/{username}")
    @Operation(summary = "踢出用户")
    public CommonResult<Boolean> kickout(@PathVariable("username") String username) {
        return CommonResult.success(backendService.kickout(username));
    }

    @Operation(summary = "更新用户角色")
    @PostMapping("/updateUserRole")
    public CommonResult<Boolean> updateUserRole(@RequestBody UpdateUserRoleDto updateUserRoleDto) {
        return CommonResult.success(backendService.updateUserRole(updateUserRoleDto));
    }
}
