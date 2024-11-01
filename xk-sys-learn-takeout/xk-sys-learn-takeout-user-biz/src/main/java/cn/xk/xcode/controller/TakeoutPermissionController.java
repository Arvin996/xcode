package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.entity.dto.permission.AddPermissionDto;
import cn.xk.xcode.entity.dto.permission.PermissionBaseDto;
import cn.xk.xcode.entity.dto.permission.UpdatePermissionDto;
import cn.xk.xcode.entity.dto.permission.QueryPermissionDto;
import cn.xk.xcode.entity.vo.permission.PermissionResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.TakeoutPermissionService;
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
 * @Date 2024/10/30 8:59
 * @Version 1.0.0
 * @Description TakeoutPermissionController
 **/
@RestController
@RequestMapping("/takeout/permission")
@Tag(name = "外卖用户用户权限接口")
public class TakeoutPermissionController {

    @Resource
    private TakeoutPermissionService takeoutPermissionService;

    @PostMapping("/add")
    @Operation(summary = "添加权限")
    @SaCheckPermission("takeout:permission:add")
    public CommonResult<Boolean> addPermission(@Validated @RequestBody AddPermissionDto addPermissionDto) {
        return CommonResult.success(takeoutPermissionService.addPermission(addPermissionDto));
    }

    @PostMapping("/del")
    @Operation(summary = "删除权限")
    @SaCheckPermission("takeout:permission:del")
    public CommonResult<Boolean> delPermission(@Validated @RequestBody PermissionBaseDto permissionBaseDto) {
        return CommonResult.success(takeoutPermissionService.delPermission(permissionBaseDto));
    }

    @PostMapping("/update")
    @Operation(summary = "修改权限")
    @SaCheckPermission("takeout:permission:update")
    public CommonResult<Boolean> updatePermission(@Validated @RequestBody UpdatePermissionDto updatePermissionDto) {
        return CommonResult.success(takeoutPermissionService.updatePermission(updatePermissionDto));
    }

    @PostMapping("/list")
    @Operation(summary = "查询权限列表")
    @SaCheckPermission("takeout:permission:list")
    public CommonResult<PageResult<PermissionResultVo>> getPermissionList(@RequestBody QueryPermissionDto queryPermissionDto){
        return CommonResult.success(takeoutPermissionService.getPermissionList(queryPermissionDto));
    }
}
