package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.user.UpdateUserDto;
import cn.xk.xcode.entity.po.RolePo;
import cn.xk.xcode.entity.po.UserRolePo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.RoleService;
import cn.xk.xcode.service.UserRoleService;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.UserPo;
import cn.xk.xcode.mapper.UserMapper;
import cn.xk.xcode.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.xk.xcode.entity.def.UserRoleTableDef.USER_ROLE_PO;
import static cn.xk.xcode.entity.def.UserTableDef.USER_PO;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.QUERY_FAILED;

/**
 *  服务层实现。
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
    public CommonResult<List<RolePo>> queryRolesByUserId(Integer id) {
        List<UserRolePo> list = userRoleService.list(USER_ROLE_PO.USER_ID.eq(id));
        List<RolePo> rolePos = roleService.listByIds(list.stream().mapToInt(UserRolePo::getRoleId).boxed().collect(Collectors.toList()));
        return CommonResult.success(rolePos);
    }

    @Override
    public CommonResult<UserPo> queryByUsername(String userName) {
        UserPo userPo = getOne(USER_PO.USERNAME.eq(userName));
        if (Objects.isNull(userPo)){
            return CommonResult.error(QUERY_FAILED);
        }
        return CommonResult.success(userPo);
    }
}
