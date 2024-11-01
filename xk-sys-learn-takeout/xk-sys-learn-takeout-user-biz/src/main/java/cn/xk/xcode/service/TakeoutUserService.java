package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.address.UpdateAddressDto;
import cn.xk.xcode.entity.dto.user.*;
import cn.xk.xcode.entity.vo.TakeoutUserResultVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.TakeoutUserPo;

import java.util.Collection;
import java.util.List;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-10-29
 */
public interface TakeoutUserService extends IService<TakeoutUserPo> {

    TakeoutUserResultVo getTakeoutUser(Long userId);

    List<TakeoutUserResultVo> getTakeoutUserList(Collection<Long> ids);

    List<TakeoutUserResultVo> getUserListByName(String name);

    TakeoutUserResultVo getUserByMobile(String mobile);

    Boolean addTakeoutUser(AddUserDto addUserDto);

    Boolean updateUserMsg(UpdateAddressDto updateAddressDto);

    Boolean updatePassword(UpdatePasswordDto updatePasswordDto);

    Boolean updateRole(UpdateUserRoleDto updateUserRoleDto);

    Boolean updateStatus(UpdateUserStatusDto updateUserStatusDto);

    Boolean updateMobile(UpdateUserMobileDto updateUserMobileDto);
}
