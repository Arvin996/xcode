package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.resource.UpdateRoleResourcesDto;
import cn.xk.xcode.entity.po.RoleResourcePo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.RoleResourceService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.ResourcePo;
import cn.xk.xcode.mapper.ResourceMapper;
import cn.xk.xcode.service.ResourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static cn.xk.xcode.entity.def.RoleResourceTableDef.ROLE_RESOURCE_PO;

/**
 *  服务层实现。
 *
 * @author Arvin
 * @since 2024-06-21
 */
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, ResourcePo> implements ResourceService {

    @Resource
    private RoleResourceService roleResourceService;

    @Transactional
    @Override
    public CommonResult<Boolean> updateRoleResources(UpdateRoleResourcesDto updateRoleResourcesDto) {
        Integer roleId = updateRoleResourcesDto.getId();
        roleResourceService.remove(ROLE_RESOURCE_PO.ROLE_ID.eq(roleId));
        for (Integer resourceId : updateRoleResourcesDto.getResourceIds()) {
            RoleResourcePo roleResourcePo = new RoleResourcePo();
            roleResourcePo.setRoleId(roleId);
            roleResourcePo.setResourceId(resourceId);
            roleResourceService.save(roleResourcePo);
        }
        return CommonResult.success(true);
    }
}
