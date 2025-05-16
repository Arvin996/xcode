package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.user.UpdatePasswordDto;
import cn.xk.xcode.entity.dto.user.UpdateUserDto;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.SystemUserPo;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-05-09
 */
public interface SystemUserService extends IService<SystemUserPo> {

    Boolean updateUserInfo(UpdateUserDto updateUserDto);

    Boolean updateUserPassword(UpdatePasswordDto updatePasswordDto);
}
