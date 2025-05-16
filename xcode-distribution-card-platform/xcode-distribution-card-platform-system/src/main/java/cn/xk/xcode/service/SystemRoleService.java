package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.role.AddRoleDto;
import cn.xk.xcode.entity.dto.role.BindRoleMenusDto;
import cn.xk.xcode.entity.dto.role.QueryRoleDto;
import cn.xk.xcode.entity.dto.role.UpdateRoleDto;
import cn.xk.xcode.entity.vo.role.SystemRoleVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.SystemRolePo;

import java.util.List;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public interface SystemRoleService extends IService<SystemRolePo> {

    List<SystemRoleVo> queryAllRoles(QueryRoleDto queryRoleDto);

    Boolean addRole(AddRoleDto addRoleDto);

    Boolean delRole(Integer id);

    Boolean updateRole(UpdateRoleDto updateRoleDto);

    Boolean bindRoleMenus(BindRoleMenusDto bindRoleMenusDto);
}
