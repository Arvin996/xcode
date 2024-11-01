package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.permission.AddPermissionDto;
import cn.xk.xcode.entity.dto.permission.PermissionBaseDto;
import cn.xk.xcode.entity.dto.permission.UpdatePermissionDto;
import cn.xk.xcode.entity.dto.permission.QueryPermissionDto;
import cn.xk.xcode.entity.vo.permission.PermissionResultVo;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.TakeoutPermissionPo;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-10-29
 */
public interface TakeoutPermissionService extends IService<TakeoutPermissionPo> {

    Boolean addPermission(AddPermissionDto addPermissionDto);

    Boolean delPermission(PermissionBaseDto permissionBaseDto);

    Boolean updatePermission(UpdatePermissionDto updatePermissionDto);

    PageResult<PermissionResultVo> getPermissionList(QueryPermissionDto queryPermissionDto);
}
