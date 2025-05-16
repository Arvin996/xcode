package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.menu.AddMenuDto;
import cn.xk.xcode.entity.dto.menu.QueryMenuDto;
import cn.xk.xcode.entity.dto.menu.UpdateMenuDto;
import cn.xk.xcode.entity.vo.menu.SystemMenuVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.query.QueryOrderBy;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SystemMenuPo;
import cn.xk.xcode.mapper.SystemMenuMapper;
import cn.xk.xcode.service.SystemMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static cn.xk.xcode.config.DistributionCardSystemErrorCode.MENU_ALREADY_EXISTS;
import static cn.xk.xcode.config.DistributionCardSystemErrorCode.MENU_NOT_EXISTS;
import static cn.xk.xcode.entity.def.SystemMenuTableDef.SYSTEM_MENU_PO;
import static cn.xk.xcode.entity.def.SystemRoleMenuTableDef.SYSTEM_ROLE_MENU_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Service
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuMapper, SystemMenuPo> implements SystemMenuService {

    @Override
    public List<SystemMenuVo> queryAllMenu(QueryMenuDto queryMenuDto) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SYSTEM_MENU_PO.ALL_COLUMNS)
                .from(SYSTEM_ROLE_MENU_PO)
                .leftJoin(SYSTEM_MENU_PO)
                .on(SYSTEM_ROLE_MENU_PO.MENU_ID.eq(SYSTEM_MENU_PO.ID))
                .where(SYSTEM_ROLE_MENU_PO.ROLE_ID.eq(queryMenuDto.getRoleId()))
                .orderBy("sort asc", "id asc");
        List<SystemMenuVo> systemMenuVos = this.listAs(queryWrapper, SystemMenuVo.class);
        Map<Long, SystemMenuVo> systemMenuVoMap = new HashMap<>();
        systemMenuVos.forEach(systemMenuVo -> systemMenuVoMap.put(systemMenuVo.getId(), systemMenuVo));
        List<SystemMenuVo> systemMenuVoList = new ArrayList<>();
        for (SystemMenuVo systemMenuVo : systemMenuVos) {
            if (0 == systemMenuVo.getParentId()) {
                systemMenuVo.setChildren(new ArrayList<>());
                systemMenuVoList.add(systemMenuVo);
            } else {
                SystemMenuVo parentSystemMenuVo = systemMenuVoMap.get(systemMenuVo.getParentId());
                if (Objects.nonNull(parentSystemMenuVo)) {
                    if (Objects.isNull(parentSystemMenuVo.getChildren())) {
                        parentSystemMenuVo.setChildren(new ArrayList<>());
                    }
                    parentSystemMenuVo.getChildren().add(systemMenuVo);
                }
            }
        }
        return systemMenuVoList;
    }

    @Override
    public Boolean addSystemMenu(AddMenuDto addMenuDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(SYSTEM_MENU_PO.NAME.eq(addMenuDto.getName()).and(SYSTEM_MENU_PO.PARENT_ID.eq(addMenuDto.getParentId()))))) {
            ExceptionUtil.castServiceException(MENU_ALREADY_EXISTS, addMenuDto.getPath());
        }
        SystemMenuPo systemMenuPo = BeanUtil.toBean(addMenuDto, SystemMenuPo.class);
        this.save(systemMenuPo);
        if (ObjectUtil.isNull(addMenuDto.getSort())) {
            systemMenuPo.setSort(Math.toIntExact(systemMenuPo.getId()));
            this.updateById(systemMenuPo);
        }
        return true;
    }

    @Transactional
    @Override
    public Boolean delSystemMenu(Long id) {
        // 需要递归删除该目录及子目录
        SystemMenuPo systemMenuPo = this.getById(id);
        if (ObjectUtil.isNull(systemMenuPo)) {
            return true;
        }
        List<Long> list = new ArrayList<>();
        list.add(id);
        // 递归获取递归该菜单及子菜单id
        this.getChildrenMenuId(list, id);
        return this.removeByIds(list);
    }

    @Override
    public Boolean updateSystemMenu(UpdateMenuDto updateMenuDto) {
        SystemMenuPo systemMenuPo = this.getById(updateMenuDto.getId());
        if (ObjectUtil.isNull(systemMenuPo)) {
            ExceptionUtil.castServiceException(MENU_NOT_EXISTS);
        }
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(SYSTEM_MENU_PO.NAME.eq(updateMenuDto.getName())
                .and(SYSTEM_MENU_PO.PARENT_ID.eq(systemMenuPo.getParentId())
                        .and(SYSTEM_MENU_PO.ID.ne(updateMenuDto.getId())))))) {
            ExceptionUtil.castServiceException(MENU_ALREADY_EXISTS, updateMenuDto.getPath());
        }
        return this.updateById(BeanUtil.toBean(updateMenuDto, SystemMenuPo.class));
    }

    @Override
    public Boolean moveUpSystemMenu(Long id) {
        SystemMenuPo systemMenuPo = this.getById(id);
        if (ObjectUtil.isNull(systemMenuPo)) {
            ExceptionUtil.castServiceException(MENU_NOT_EXISTS);
        }
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SYSTEM_MENU_PO.ALL_COLUMNS)
                .from(SYSTEM_MENU_PO)
                .where(SYSTEM_MENU_PO.PARENT_ID.eq(systemMenuPo.getParentId()))
                .orderBy("sort asc", "id asc");
        List<SystemMenuPo> menuPoList = this.list(queryWrapper);
        int index = 0;
        for (int i = 0; i < menuPoList.size(); i++) {
            if (Objects.equals(menuPoList.get(i).getId(), id)) {
                index = i;
                break;
            }
        }
        if (index == 0) {
            return true;
        } else {
            SystemMenuPo preSystemMenuPo = menuPoList.get(index - 1);
            systemMenuPo.setSort(preSystemMenuPo.getSort() - 1);
            return this.updateById(systemMenuPo);
        }
    }

    @Override
    public Boolean moveDownSystemMenu(Long id) {
        SystemMenuPo systemMenuPo = this.getById(id);
        if (ObjectUtil.isNull(systemMenuPo)) {
            ExceptionUtil.castServiceException(MENU_NOT_EXISTS);
        }
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SYSTEM_MENU_PO.ALL_COLUMNS)
                .from(SYSTEM_MENU_PO)
                .where(SYSTEM_MENU_PO.PARENT_ID.eq(systemMenuPo.getParentId()))
                .orderBy("sort asc", "id asc");
        List<SystemMenuPo> menuPoList = this.list(queryWrapper);
        int index = 0;
        for (int i = 0; i < menuPoList.size(); i++) {
            if (Objects.equals(menuPoList.get(i).getId(), id)) {
                index = i;
                break;
            }
        }
        if (index == menuPoList.size()) {
            return true;
        } else {
            SystemMenuPo nextSystemMenuPo = menuPoList.get(index + 1);
            systemMenuPo.setSort(nextSystemMenuPo.getSort() + 1);
            return this.updateById(systemMenuPo);
        }
    }

    private void getChildrenMenuId(List<Long> list, Long id) {
        // 递归获取递归该菜单及子菜单id
        list.add(id);
        List<SystemMenuPo> menuPoList = this.list(SYSTEM_MENU_PO.PARENT_ID.eq(id));
        if (CollectionUtil.isNotEmpty(menuPoList)) {
            for (SystemMenuPo systemMenuPo : menuPoList) {
                list.add(systemMenuPo.getId());
                this.getChildrenMenuId(list, systemMenuPo.getId());
            }
        }
    }
}
