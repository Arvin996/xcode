package cn.xk.xcode.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.dto.permission.AddPermissionDto;
import cn.xk.xcode.entity.dto.permission.PermissionBaseDto;
import cn.xk.xcode.entity.dto.permission.UpdatePermissionDto;
import cn.xk.xcode.entity.dto.permission.QueryPermissionDto;
import cn.xk.xcode.entity.vo.permission.PermissionResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.TakeoutRolePermissionService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.TakeoutPermissionPo;
import cn.xk.xcode.mapper.TakeoutPermissionMapper;
import cn.xk.xcode.service.TakeoutPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static cn.xk.xcode.constant.GlobalTakeoutUserConstant.PERMISSION_CODE_ALREADY_EXISTS;
import static cn.xk.xcode.entity.def.TakeoutPermissionTableDef.TAKEOUT_PERMISSION_PO;
import static cn.xk.xcode.entity.def.TakeoutRolePermissionTableDef.TAKEOUT_ROLE_PERMISSION_PO;

/**
 *  服务层实现。
 *
 * @author Administrator
 * @since 2024-10-29
 */
@Service
public class TakeoutPermissionServiceImpl extends ServiceImpl<TakeoutPermissionMapper, TakeoutPermissionPo> implements TakeoutPermissionService {

    @Resource
    private TakeoutRolePermissionService takeoutRolePermissionService;

    @Override
    public Boolean addPermission(AddPermissionDto addPermissionDto) {
        validate(addPermissionDto);
        return this.save(TakeoutPermissionPo.builder().code(addPermissionDto.getCode()).build());
    }

    @Transactional
    @Override
    public Boolean delPermission(PermissionBaseDto permissionBaseDto) {
        // 删除角色权限关联
        takeoutRolePermissionService.remove(TAKEOUT_ROLE_PERMISSION_PO.PERMISSION_ID.eq(permissionBaseDto.getId()));
        return this.removeById(permissionBaseDto.getId());
    }

    @Override
    public Boolean updatePermission(UpdatePermissionDto updatePermissionDto) {
        validate(updatePermissionDto);
        return this.updateById(TakeoutPermissionPo.builder().id(updatePermissionDto.getId()).code(updatePermissionDto.getCode()).build());
    }

    @Override
    public PageResult<PermissionResultVo> getPermissionList(QueryPermissionDto queryPermissionDto) {
        Page<TakeoutPermissionPo> page = this.page(new Page<>(queryPermissionDto.getPageNo(), queryPermissionDto.getPageSize()), TAKEOUT_PERMISSION_PO.CODE.like(queryPermissionDto.getCode()).when(StrUtil.isNotBlank(queryPermissionDto.getCode())));
        return new PageResult<>(page.getPageNumber(), page.getPageSize(), page.getTotalPage(), CollectionUtil.convertList(page.getRecords(), v -> BeanUtil.toBean(v, PermissionResultVo.class)));
    }

    private void validate(AddPermissionDto addPermissionDto){
        if (this.count(TAKEOUT_PERMISSION_PO.CODE.eq(addPermissionDto.getCode())) > 0){
            ExceptionUtil.castServiceException(PERMISSION_CODE_ALREADY_EXISTS, addPermissionDto.getCode());
        }
    }

    private void validate(UpdatePermissionDto updatePermissionDto){
        if (this.count(TAKEOUT_PERMISSION_PO.CODE.eq(updatePermissionDto.getCode()).and(TAKEOUT_PERMISSION_PO.ID.ne(updatePermissionDto.getId()))) > 0){
            ExceptionUtil.castServiceException(PERMISSION_CODE_ALREADY_EXISTS, updatePermissionDto.getCode());
        }
    }
}
