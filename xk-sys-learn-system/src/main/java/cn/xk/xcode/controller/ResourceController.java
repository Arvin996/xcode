package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.convert.ResourceConvert;
import cn.xk.xcode.entity.dto.resource.AddResourceDto;
import cn.xk.xcode.entity.dto.resource.UpdateResourceDto;
import cn.xk.xcode.entity.dto.resource.UpdateRoleResourcesDto;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.ResourceService;
import cn.xk.xcode.service.RoleResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.xk.xcode.entity.def.RoleResourceTableDef.ROLE_RESOURCE_PO;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 19:17
 * @description
 */
@RestController
@Tag(name = "资源管理")
@RequestMapping("/resource")
public class ResourceController
{
    @Resource
    private ResourceService resourceService;

    @Resource
    private ResourceConvert resourceConvert;

    @Resource
    private RoleResourceService roleResourceService;

    @SaCheckPermission("resource:addResource")
    @Operation(summary = "新增资源")
    @PostMapping("/addResource")
    public CommonResult<Boolean> addResource(@Validated @RequestBody AddResourceDto addResourceDto){
        return CommonResult.success(resourceService.save(resourceConvert.addResourceDtoToVo(addResourceDto)));
    }


    @SaCheckPermission("resource:deleteResource")
    @Operation(summary = "删除资源")
    @PostMapping("/deleteResource")
    public CommonResult<Boolean> deleteResource(@RequestBody UpdateResourceDto updateResourceDto){
        if (roleResourceService.count(ROLE_RESOURCE_PO.ROLE_ID.eq(updateResourceDto.getId())) > 0){
            return CommonResult.error("500", "该资源已被角色使用，不能删除");
        }
        return CommonResult.success(resourceService.removeById(updateResourceDto.getId()));
    }

    @SaCheckPermission("resource:updateResource")
    @Operation(summary = "修改资源")
    @PostMapping("/updateResource")
    public CommonResult<Boolean> updateResource(@Validated @RequestBody UpdateResourceDto updateResourceDto){
        return CommonResult.success(resourceService.updateById(resourceConvert.updateResourceDtoToVo(updateResourceDto)));
    }

    @SaCheckPermission("resource:updateRoleResources")
    @Operation(summary = "更新角色资源")
    @PostMapping("/updateRoleResources")
    public CommonResult<Boolean> updateRoleResources(@Validated @RequestBody UpdateRoleResourcesDto updateRoleResourcesDto){
       return resourceService.updateRoleResources(updateRoleResourcesDto);
    }

}
