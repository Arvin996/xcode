package cn.xk.xcode.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.dto.role.AddRoleDto;
import cn.xk.xcode.entity.dto.role.BindRoleMenusDto;
import cn.xk.xcode.entity.dto.role.QueryRoleDto;
import cn.xk.xcode.entity.dto.role.UpdateRoleDto;
import cn.xk.xcode.entity.po.SystemRoleMenuPo;
import cn.xk.xcode.entity.vo.role.SystemRoleVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.SystemRoleMenuService;
import cn.xk.xcode.service.SystemUserService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SystemRolePo;
import cn.xk.xcode.mapper.SystemRoleMapper;
import cn.xk.xcode.service.SystemRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static cn.xk.xcode.config.DistributionCardSystemErrorCode.*;
import static cn.xk.xcode.entity.def.SystemRoleMenuTableDef.SYSTEM_ROLE_MENU_PO;
import static cn.xk.xcode.entity.def.SystemRoleTableDef.SYSTEM_ROLE_PO;
import static cn.xk.xcode.entity.def.SystemUserTableDef.SYSTEM_USER_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Service
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRolePo> implements SystemRoleService {

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private SystemRoleMenuService systemRoleMenuService;

    @Override
    public List<SystemRoleVo> queryAllRoles(QueryRoleDto queryRoleDto) {
        QueryWrapper queryWrapper = QueryWrapper.create().select(SYSTEM_ROLE_PO.ALL_COLUMNS).from(SYSTEM_ROLE_PO).where("1=1").and(SYSTEM_ROLE_PO.CODE.likeRight(queryRoleDto.getCode()).when(StrUtil.isNotBlank(queryRoleDto.getCode()))).and(SYSTEM_ROLE_PO.NAME.likeRight(queryRoleDto.getName()).when(StrUtil.isNotBlank(queryRoleDto.getName()))).orderBy(SYSTEM_ROLE_PO.ID, true);
        return this.listAs(queryWrapper, SystemRoleVo.class);
    }

    @Override
    public Boolean addRole(AddRoleDto addRoleDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(SYSTEM_ROLE_PO.NAME.eq(addRoleDto.getName())))) {
            ExceptionUtil.castServiceException(ROLE_NAME_ALREADY_EXISTS, addRoleDto.getName());
        }
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(SYSTEM_ROLE_PO.CODE.eq(addRoleDto.getCode())))) {
            ExceptionUtil.castServiceException(ROLE_CODE_ALREADY_EXISTS, addRoleDto.getCode());
        }
        return this.save(BeanUtil.toBean(addRoleDto, SystemRolePo.class));
    }

    @Transactional
    @Override
    public Boolean delRole(Integer id) {
        if (systemUserService.exists(SYSTEM_USER_PO.ROLE_ID.eq(id))) {
            ExceptionUtil.castServiceException(ROLE_HAS_BINDING_USER);
        }
        systemRoleMenuService.remove(SYSTEM_ROLE_MENU_PO.ROLE_ID.eq(id));
        return this.removeById(id);
    }

    @Override
    public Boolean updateRole(UpdateRoleDto updateRoleDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(SYSTEM_ROLE_PO.NAME.eq(updateRoleDto.getName()).and(SYSTEM_ROLE_PO.ID.ne(updateRoleDto.getId()))))) {
            ExceptionUtil.castServiceException(ROLE_NAME_ALREADY_EXISTS, updateRoleDto.getName());
        }
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(SYSTEM_ROLE_PO.CODE.eq(updateRoleDto.getCode()).and(SYSTEM_ROLE_PO.ID.ne(updateRoleDto.getId()))))) {
            ExceptionUtil.castServiceException(ROLE_CODE_ALREADY_EXISTS, updateRoleDto.getCode());
        }
        return this.updateById(BeanUtil.toBean(updateRoleDto, SystemRolePo.class));
    }

    @Transactional
    @Override
    public Boolean bindRoleMenus(BindRoleMenusDto bindRoleMenusDto) {
        List<Long> menuIds = bindRoleMenusDto.getMenuIds();
        if (CollectionUtil.isEmpty(menuIds)) {
            return systemRoleMenuService.remove(SYSTEM_ROLE_MENU_PO.ROLE_ID.eq(bindRoleMenusDto.getRoleId()));
        } else {
            systemRoleMenuService.remove(SYSTEM_ROLE_MENU_PO.ROLE_ID.eq(bindRoleMenusDto.getRoleId()));
            List<SystemRoleMenuPo> collect = menuIds.stream().map(id -> {
                SystemRoleMenuPo systemRoleMenuPo = new SystemRoleMenuPo();
                systemRoleMenuPo.setMenuId(Math.toIntExact(id));
                systemRoleMenuPo.setRoleId(bindRoleMenusDto.getRoleId());
                return systemRoleMenuPo;
            }).collect(Collectors.toList());
            return systemRoleMenuService.saveBatch(collect);
        }
    }
}
