package cn.xk.xcode.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.dto.role.*;
import cn.xk.xcode.entity.po.TakeoutRolePermissionPo;
import cn.xk.xcode.entity.vo.role.RoleResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.TakeoutPermissionService;
import cn.xk.xcode.service.TakeoutRolePermissionService;
import cn.xk.xcode.service.TakeoutUserService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.TakeoutRolePo;
import cn.xk.xcode.mapper.TakeoutRoleMapper;
import cn.xk.xcode.service.TakeoutRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static cn.xk.xcode.constant.GlobalTakeoutUserConstant.*;
import static cn.xk.xcode.entity.def.TakeoutPermissionTableDef.TAKEOUT_PERMISSION_PO;
import static cn.xk.xcode.entity.def.TakeoutRolePermissionTableDef.TAKEOUT_ROLE_PERMISSION_PO;
import static cn.xk.xcode.entity.def.TakeoutRoleTableDef.TAKEOUT_ROLE_PO;
import static cn.xk.xcode.entity.def.TakeoutUserTableDef.TAKEOUT_USER_PO;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-10-29
 */
@Service
public class TakeoutRoleServiceImpl extends ServiceImpl<TakeoutRoleMapper, TakeoutRolePo> implements TakeoutRoleService {

    @Resource
    private TakeoutUserService takeoutUserService;

    @Resource
    private TakeoutRolePermissionService takeoutRolePermissionService;

    @Override
    public Boolean addRole(AddRoleDto addRoleDto) {
        String name = addRoleDto.getName();
        validate(addRoleDto);
        return this.save(TakeoutRolePo.builder().name(name).build());
    }

    @Override
    public Boolean delRole(RoleBaseDto roleBaseDto) {
        validate(roleBaseDto);
        return this.removeById(roleBaseDto.getId());
    }

    @Override
    public Boolean updateRole(UpdateRoleDto updateRoleDto) {
        if (this.count(TAKEOUT_ROLE_PO.NAME.eq(updateRoleDto.getName()).and(TAKEOUT_ROLE_PO.ID.ne(updateRoleDto.getId()))) > 0) {
            ExceptionUtil.castServiceException(ROLE_NAME_ALREADY_EXISTS, updateRoleDto.getName());
        }
        return this.updateById(TakeoutRolePo.builder().id(updateRoleDto.getId()).name(updateRoleDto.getName()).build());
    }

    @Override
    public List<RoleResultVo> getRoleList(QueryRoleDto queryRoleDto) {
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .select(TAKEOUT_ROLE_PO.ALL_COLUMNS)
                .from(TAKEOUT_ROLE_PO)
                .where(TAKEOUT_ROLE_PO.NAME.like(queryRoleDto.getName(), StrUtil.isNotBlank(queryRoleDto.getName())))
                .and(TAKEOUT_ROLE_PO.ID.eq(queryRoleDto.getId(), Objects.nonNull(queryRoleDto.getId())));
        List<TakeoutRolePo> takeoutRolePoList = this.list(queryWrapper);
        List<RoleResultVo> roleResultVoList = new ArrayList<>();
        if (takeoutRolePoList.isEmpty()) {
            return Collections.emptyList();
        }
        takeoutRolePoList.forEach(v -> {
            RoleResultVo roleResultVo = new RoleResultVo();
            BeanUtils.copyProperties(v, roleResultVo);
            QueryWrapper qw = QueryWrapper.create()
                    .select(TAKEOUT_PERMISSION_PO.CODE)
                    .from(TAKEOUT_ROLE_PO)
                    .leftJoin(TAKEOUT_ROLE_PERMISSION_PO)
                    .on(TAKEOUT_ROLE_PO.ID.eq(TAKEOUT_ROLE_PERMISSION_PO.ROLE_ID))
                    .leftJoin(TAKEOUT_PERMISSION_PO)
                    .on(TAKEOUT_PERMISSION_PO.ID.eq(TAKEOUT_ROLE_PERMISSION_PO.PERMISSION_ID));
            roleResultVo.setPermissionList(this.listAs(qw, String.class));
        });
        return roleResultVoList;
    }

    @Transactional
    @Override
    public Boolean bindRolePermission(BindRolePermission bindRolePermission) {
        Integer roleId = bindRolePermission.getRoleId();
        takeoutRolePermissionService.remove(TAKEOUT_ROLE_PERMISSION_PO.ROLE_ID.eq(roleId));
        List<Integer> permissionIdList = bindRolePermission.getPermissionList();
        if (permissionIdList.isEmpty()) {
            return true;
        }
        List<TakeoutRolePermissionPo> takeoutRolePermissionPoList = new ArrayList<>();
        permissionIdList.forEach(v -> takeoutRolePermissionPoList.add(TakeoutRolePermissionPo.builder().roleId(roleId).permissionId(v).build()));
        return takeoutRolePermissionService.saveBatch(takeoutRolePermissionPoList);
    }

    private void validate(AddRoleDto addRoleDto) {
        if (this.count(TAKEOUT_ROLE_PO.NAME.eq(addRoleDto.getName())) > 0) {
            ExceptionUtil.castServiceException(ROLE_NAME_ALREADY_EXISTS, addRoleDto.getName());
        }
    }

    private void validate(RoleBaseDto roleBaseDto) {
        if (takeoutUserService.count(TAKEOUT_USER_PO.ROLE_ID.eq(roleBaseDto.getId())) > 0) {
            ExceptionUtil.castServiceException(ROLE_HAS_BIND_USER);
        }
        if (takeoutRolePermissionService.count(TAKEOUT_ROLE_PERMISSION_PO.ROLE_ID.eq(roleBaseDto.getId())) > 0) {
            ExceptionUtil.castServiceException(ROLE_HAS_BIND_PERMISSION);
        }
    }
}
