package cn.xk.xcode.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.dto.user.UpdatePasswordDto;
import cn.xk.xcode.entity.dto.user.UpdateUserDto;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.SystemUserPo;
import cn.xk.xcode.mapper.SystemUserMapper;
import cn.xk.xcode.service.SystemUserService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static cn.xk.xcode.config.DistributionCardSystemErrorCode.*;
import static cn.xk.xcode.entity.def.SystemUserTableDef.SYSTEM_USER_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-05-09
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUserPo> implements SystemUserService {

    private final static Double DEFAULT_PASSWORD_SIMILARITY_THRESHOLD = 80.0d;

    @Override
    public Boolean updateUserInfo(UpdateUserDto updateUserDto) {
        SystemUserPo systemUserPo = this.getOne(SYSTEM_USER_PO.USERNAME.eq(updateUserDto.getUsername()));
        if (ObjectUtil.isNull(systemUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(systemUserPo.getStatus())) {
            ExceptionUtil.castServiceException(ACCOUNT_IS_DISABLED);
        }
        BeanUtil.copyProperties(updateUserDto, systemUserPo);
        return this.updateById(systemUserPo);
    }

    @Override
    public Boolean updateUserPassword(UpdatePasswordDto updatePasswordDto) {
        SystemUserPo systemUserPo = this.getOne(SYSTEM_USER_PO.USERNAME.eq(updatePasswordDto.getUsername()));
        if (ObjectUtil.isNull(systemUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(systemUserPo.getStatus())) {
            ExceptionUtil.castServiceException(ACCOUNT_IS_DISABLED);
        }
        if (!systemUserPo.getPassword().equals(updatePasswordDto.getOldPassword())) {
            ExceptionUtil.castServiceException(ORIGINAL_PASSWORD_ERROR);
        }
        if (!updatePasswordDto.getNewPassword().equals(updatePasswordDto.getConfirmPassword())) {
            ExceptionUtil.castServiceException(PASSWORD_CONFIRM_NOT_EQUAL);
        }
        if (updatePasswordDto.getNewPassword().equals(updatePasswordDto.getOldPassword())) {
            ExceptionUtil.castServiceException(NEW_OLD_PASSWORD_MUST_NOT_EQUAL);
        }
        if (StrUtil.similar(updatePasswordDto.getOldPassword(), updatePasswordDto.getNewPassword()) >= DEFAULT_PASSWORD_SIMILARITY_THRESHOLD) {
            ExceptionUtil.castServiceException(NEW_OLD_PASSWORD_SIMILARITY);
        }
        systemUserPo.setPassword(updatePasswordDto.getNewPassword());
        return this.updateById(systemUserPo);
    }


}
