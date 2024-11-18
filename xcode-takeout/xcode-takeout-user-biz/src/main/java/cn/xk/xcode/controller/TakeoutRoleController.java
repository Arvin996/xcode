package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.entity.dto.role.*;
import cn.xk.xcode.entity.vo.role.RoleResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.TakeoutRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/10/29 15:58
 * @Version 1.0.0
 * @Description TakeoutRoleController
 **/
@RestController
@RequestMapping("/takeout/role")
@Tag(name = "外卖系统角色接口")
public class TakeoutRoleController {

    @Resource
    private TakeoutRoleService takeoutRoleService;

    @PostMapping("/add")
    @Operation(summary = "添加角色")
    @SaCheckPermission("takeout:role:add")
    public CommonResult<Boolean> addRole(@Validated @RequestBody AddRoleDto addRoleDto){
        return CommonResult.success(takeoutRoleService.addRole(addRoleDto));
    }

    @PostMapping("/del")
    @Operation(summary = "删除角色")
    @SaCheckPermission("takeout:role:del")
    public CommonResult<Boolean> delRole(@Validated @RequestBody RoleBaseDto roleBaseDto){
        return CommonResult.success(takeoutRoleService.delRole(roleBaseDto));
    }

    @PostMapping("/update")
    @Operation(summary = "更新角色")
    @SaCheckPermission("takeout:role:update")
    public CommonResult<Boolean> updateRole(@Validated @RequestBody UpdateRoleDto updateRoleDto){
        return CommonResult.success(takeoutRoleService.updateRole(updateRoleDto));
    }

    @PostMapping("/list")
    @Operation(summary = "查询角色列表")
    @SaCheckPermission("takeout:role:list")
    public CommonResult<List<RoleResultVo>> getRoleList(@RequestBody QueryRoleDto queryRoleDto){
        return CommonResult.success(takeoutRoleService.getRoleList(queryRoleDto));
    }

    @PostMapping("/bindRolePermission")
    @Operation(summary = "绑定角色权限")
    @SaCheckPermission("takeout:role:bindRolePermission")
    public CommonResult<Boolean> bindRolePermission(@Validated @RequestBody BindRolePermission bindRolePermission){
        return CommonResult.success(takeoutRoleService.bindRolePermission(bindRolePermission));
    }
}
