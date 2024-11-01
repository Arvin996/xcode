package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.role.*;
import cn.xk.xcode.entity.vo.role.RoleResultVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.TakeoutRolePo;

import java.util.List;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-10-29
 */
public interface TakeoutRoleService extends IService<TakeoutRolePo> {

    Boolean addRole(AddRoleDto addRoleDto);

    Boolean delRole(RoleBaseDto roleBaseDto);

    Boolean updateRole(UpdateRoleDto updateRoleDto);

    List<RoleResultVo> getRoleList(QueryRoleDto queryRoleDto);

    Boolean bindRolePermission(BindRolePermission bindRolePermission);
}
