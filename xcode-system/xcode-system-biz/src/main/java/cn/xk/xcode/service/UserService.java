package cn.xk.xcode.service;

import cn.xk.xcode.entity.SystemUserResultVo;
import cn.xk.xcode.entity.dto.user.UpdateUserDto;
import cn.xk.xcode.entity.po.RolePo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginUser;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.UserPo;

import java.util.List;

/**
 *  服务层。
 *
 * @author Arvin
 * @since 2024-06-21
 */
public interface UserService extends IService<UserPo> {
    CommonResult<Boolean> deleteUser(UpdateUserDto updateUserDto);

    CommonResult<List<RolePo>> queryRolesByUserId(Long id);

    CommonResult<UserPo> queryByUsername(String userName);

    LoginUser buildLoginUser(UserPo userPo);

    SystemUserResultVo getUser(Long id);
}
