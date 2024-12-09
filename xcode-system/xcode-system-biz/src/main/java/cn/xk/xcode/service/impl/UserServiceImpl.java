package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.SystemUserResultVo;
import cn.xk.xcode.entity.dto.user.UpdateUserDto;
import cn.xk.xcode.entity.po.ResourcePo;
import cn.xk.xcode.entity.po.RolePo;
import cn.xk.xcode.entity.po.UserRolePo;
import cn.xk.xcode.exception.core.AssertUtil;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.service.RoleService;
import cn.xk.xcode.service.UserRoleService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.UserPo;
import cn.xk.xcode.mapper.UserMapper;
import cn.xk.xcode.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.*;
import java.util.stream.Collectors;

import static cn.xk.xcode.entity.def.UserRoleTableDef.USER_ROLE_PO;
import static cn.xk.xcode.entity.def.UserTableDef.USER_PO;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.QUERY_FAILED;

/**
 * 服务层实现。
 *
 * @author Arvin
 * @since 2024-06-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserPo> implements UserService {

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;

    @Override
    public CommonResult<Boolean> deleteUser(UpdateUserDto updateUserDto) {
        userRoleService.remove(USER_ROLE_PO.USER_ID.eq(updateUserDto.getId()));
        removeById(updateUserDto.getId());
        return CommonResult.success(true);
    }

    @Override
    public CommonResult<List<RolePo>> queryRolesByUserId(Long id) {
        List<UserRolePo> list = userRoleService.list(USER_ROLE_PO.USER_ID.eq(id));
        List<RolePo> rolePos = roleService.listByIds(list.stream().mapToInt(UserRolePo::getRoleId).boxed().collect(Collectors.toList()));
        return CommonResult.success(rolePos);
    }

    @Override
    public CommonResult<UserPo> queryByUsername(String userName) {
        UserPo userPo = getOne(USER_PO.USERNAME.eq(userName));
        if (Objects.isNull(userPo)) {
            return CommonResult.error(QUERY_FAILED);
        }
        return CommonResult.success(userPo);
    }

    @Override
    public LoginUser buildLoginUser(UserPo userPo) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(userPo.getUsername());
        List<RolePo> roles = queryRolesByUserId(userPo.getId()).getData();
        loginUser.setRoles(CollectionUtil.convertSet(roles, RolePo::getName));
        Set<String> recourses = new HashSet<>();
        for (RolePo role : roles) {
            recourses.addAll(CollectionUtil.convertSet(roleService.queryResourcesByRole(role.getId()).getData(), ResourcePo::getResourceCode));
        }
        loginUser.setPermissions(recourses);
        return loginUser;
    }

    @Override
    public SystemUserResultVo getUser(Long id) {
        UserPo userPo = this.getById(id);
        AssertUtil.assertNullCastServiceEx(userPo, QUERY_FAILED);
        SystemUserResultVo systemUserResultVo = new SystemUserResultVo();
        systemUserResultVo.setRoles(new ArrayList<>());
        BeanUtils.copyProperties(userPo, systemUserResultVo);
        List<RolePo> rolePos = queryRolesByUserId(id).getData();
        if (CollectionUtil.isNotEmpty(rolePos)){
            rolePos.forEach(role -> {
                systemUserResultVo.getRoles().add(new SystemUserResultVo.SystemRole().setName(role.getName()).setId(role.getId()));
            });
        }
        return systemUserResultVo;
    }
}
