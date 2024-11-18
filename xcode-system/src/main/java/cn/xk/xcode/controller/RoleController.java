package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.convert.RoleConvert;
import cn.xk.xcode.entity.dto.role.AddRoleDto;
import cn.xk.xcode.entity.dto.role.UpdateRoleDto;
import cn.xk.xcode.entity.dto.role.UpdateUserRoleDto;
import cn.xk.xcode.entity.po.ResourcePo;
import cn.xk.xcode.entity.po.RoleResourcePo;
import cn.xk.xcode.entity.po.UserPo;
import cn.xk.xcode.entity.po.UserRolePo;
import cn.xk.xcode.mapper.ResourceMapper;
import cn.xk.xcode.mapper.UserMapper;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.RoleResourceService;
import cn.xk.xcode.service.RoleService;
import cn.xk.xcode.service.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static cn.xk.xcode.entity.def.RoleResourceTableDef.ROLE_RESOURCE_PO;
import static cn.xk.xcode.entity.def.RoleTableDef.ROLE_PO;
import static cn.xk.xcode.entity.def.UserRoleTableDef.USER_ROLE_PO;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.ROLE_ADD_FAILED;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 19:17
 * @description
 */
@Tag(name = "角色管理")
@RestController("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleResourceService roleResourceService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private ResourceMapper resourceMapper;

    @Resource
    private RoleConvert roleConvert;

    @SaCheckPermission("role:addRole")
    @Operation(summary = "新增角色")
    @PostMapping("/addRole")
    public CommonResult<Boolean> addRole(@Validated @RequestBody AddRoleDto addRoleDto){
        if (roleService.exists(ROLE_PO.NAME.eq(addRoleDto.getName()))){
            CommonResult.error(ROLE_ADD_FAILED);
        }
        return CommonResult.success(roleService.save(roleConvert.addRoleDtoToPo(addRoleDto)));
    }

    @SaCheckPermission("role:delRole")
    @Operation(summary = "删除角色")
    @PostMapping("/delRole")
    public CommonResult<Boolean> delRole(@Validated @RequestBody UpdateRoleDto updateRoleDto){
        if (userRoleService.count(USER_ROLE_PO.ROLE_ID.eq(updateRoleDto.getId())) > 0){
            return CommonResult.error("500","该角色下存在用户，不能删除");
        }
        if (roleResourceService.count(ROLE_RESOURCE_PO.ROLE_ID.eq(updateRoleDto.getId())) > 0){
            return CommonResult.error("500","该角色下存在资源，不能删除");
        }
        return CommonResult.success(roleService.removeById(updateRoleDto.getId()));
    }

    @Operation(summary = "查询该角色下所有的用户")
    @PostMapping("/queryUsersByRole")
    public CommonResult<List<UserPo>> queryUsersByRole(@RequestBody UpdateRoleDto updateRoleDto)
    {
        List<UserRolePo> list = userRoleService.list(USER_ROLE_PO.ROLE_ID.eq(updateRoleDto.getId()));
        List<UserPo> userPos = userMapper.selectListByIds(list.stream().mapToInt(UserRolePo::getUserId).boxed().collect(Collectors.toList()));
        return CommonResult.success(userPos);
    }

    @Operation(summary = "查询该角色下所有的资源")
    @PostMapping("/queryResourcesByRole")
    public CommonResult<List<ResourcePo>> queryResourcesByRole(@RequestBody UpdateRoleDto updateRoleDto)
    {
        return roleService.queryResourcesByRole(updateRoleDto.getId());
    }

    @SaCheckPermission("role:updateRolesToUser")
    @Operation(summary = "更新用户角色")
    @PostMapping("/updateRolesToUser")
    public CommonResult<Boolean> updateRolesToUser(@Validated @RequestBody UpdateUserRoleDto updateUserRoleDto)
    {
       return roleService.updateRolesToUser(updateUserRoleDto);
    }


}
