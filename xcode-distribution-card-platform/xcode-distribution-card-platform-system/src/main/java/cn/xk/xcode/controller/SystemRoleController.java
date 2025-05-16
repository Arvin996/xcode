package cn.xk.xcode.controller;

import cn.xk.xcode.annotation.SaSystemCheckRole;
import cn.xk.xcode.entity.dto.role.AddRoleDto;
import cn.xk.xcode.entity.dto.role.BindRoleMenusDto;
import cn.xk.xcode.entity.dto.role.QueryRoleDto;
import cn.xk.xcode.entity.dto.role.UpdateRoleDto;
import cn.xk.xcode.entity.vo.role.SystemRoleVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.SystemRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/12 15:25
 * @Version 1.0.0
 * @Description SystemRoleController
 **/
@RestController
@RequestMapping("/manage/role")
@Tag(name = "SystemRoleController", description = "角色接口")
@SaSystemCheckRole("root")
@Validated
public class SystemRoleController {

    @Resource
    private SystemRoleService systemRoleService;

    @Operation(summary = "查询所有角色")
    @PostMapping("/queryAllRoles")
    public CommonResult<List<SystemRoleVo>> queryAllRoles(@RequestBody QueryRoleDto queryRoleDto) {
        return CommonResult.success(systemRoleService.queryAllRoles(queryRoleDto));
    }

    @Operation(summary = "新增角色")
    @PostMapping("/addRole")
    public CommonResult<Boolean> addRole(@RequestBody AddRoleDto addRoleDto) {
        return CommonResult.success(systemRoleService.addRole(addRoleDto));
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/del/{id}")
    public CommonResult<Boolean> delRole(@PathVariable("id") Integer id) {
        return CommonResult.success(systemRoleService.delRole(id));
    }

    @Operation(summary = "修改角色")
    @PostMapping("/updateRole")
    public CommonResult<Boolean> updateRole(@RequestBody UpdateRoleDto updateRoleDto) {
        return CommonResult.success(systemRoleService.updateRole(updateRoleDto));
    }

    @Operation(summary = "绑定角色菜单")
    @PostMapping("/bindRoleMenus")
    public CommonResult<Boolean> bindRoleMenus(@RequestBody BindRoleMenusDto bindRoleMenusDto) {
        return CommonResult.success(systemRoleService.bindRoleMenus(bindRoleMenusDto));
    }
}
