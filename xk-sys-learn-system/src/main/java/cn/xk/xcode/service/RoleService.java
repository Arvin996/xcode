package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.role.UpdateUserRoleDto;
import cn.xk.xcode.entity.po.ResourcePo;
import cn.xk.xcode.pojo.CommonResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.RolePo;

import java.util.List;

/**
 *  服务层。
 *
 * @author Arvin
 * @since 2024-06-21
 */
public interface RoleService extends IService<RolePo> {
    public CommonResult<Boolean> updateRolesToUser(UpdateUserRoleDto updateUserRoleDto);

    CommonResult<List<ResourcePo>> queryResourcesByRole(Integer id);
}
