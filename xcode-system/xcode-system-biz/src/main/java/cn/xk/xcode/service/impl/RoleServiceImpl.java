package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.role.UpdateUserRoleDto;
import cn.xk.xcode.entity.po.ResourcePo;
import cn.xk.xcode.entity.po.RoleResourcePo;
import cn.xk.xcode.entity.po.UserRolePo;
import cn.xk.xcode.mapper.ResourceMapper;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.RoleResourceService;
import cn.xk.xcode.service.UserRoleService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.RolePo;
import cn.xk.xcode.mapper.RoleMapper;
import cn.xk.xcode.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;
import java.util.stream.Collectors;

import static cn.xk.xcode.entity.def.RoleResourceTableDef.ROLE_RESOURCE_PO;
import static cn.xk.xcode.entity.def.UserRoleTableDef.USER_ROLE_PO;

/**
 *  服务层实现。
 *
 * @author Arvin
 * @since 2024-06-21
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RolePo> implements RoleService {

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleResourceService roleResourceService;

    @Resource
    private ResourceMapper resourceMapper;

    @Transactional
    @Override
    public CommonResult<Boolean> updateRolesToUser(UpdateUserRoleDto updateUserRoleDto) {
        List<Integer> roleIds = updateUserRoleDto.getRoleIds();
        // 删除
        userRoleService.remove(USER_ROLE_PO.USER_ID.eq(updateUserRoleDto.getUserId()));
        // 新增
        for (Integer roleId : roleIds) {
            UserRolePo userRolePo = new UserRolePo();
            userRolePo.setUserId(Long.valueOf(updateUserRoleDto.getUserId()));
            userRolePo.setRoleId(roleId);
            userRoleService.save(userRolePo);
        }
        return CommonResult.success(true);
    }

    @Override
    public CommonResult<List<ResourcePo>> queryResourcesByRole(Integer id) {
        List<RoleResourcePo> list = roleResourceService.list(ROLE_RESOURCE_PO.ROLE_ID.eq(id));
        List<ResourcePo> resourcePos = resourceMapper.selectListByIds(list.stream().mapToInt(RoleResourcePo::getResourceId).boxed().collect(Collectors.toList()));
        return CommonResult.success(resourcePos);
    }
}
