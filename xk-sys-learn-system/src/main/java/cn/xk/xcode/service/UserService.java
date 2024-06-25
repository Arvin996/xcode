package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.user.UpdateUserDto;
import cn.xk.xcode.entity.po.RolePo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginUser;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.UserPo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 *  服务层。
 *
 * @author Arvin
 * @since 2024-06-21
 */
public interface UserService extends IService<UserPo> {
    public CommonResult<Boolean> deleteUser(UpdateUserDto updateUserDto);

    public CommonResult<List<RolePo>> queryRolesByUserId(Integer id);

    CommonResult<UserPo> queryByUsername(String userName);

    public LoginUser buildLoginUser(UserPo userPo);
}
