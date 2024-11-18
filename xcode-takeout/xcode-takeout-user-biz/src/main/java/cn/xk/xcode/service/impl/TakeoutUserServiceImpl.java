package cn.xk.xcode.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.xk.xcode.client.CheckCodeClientApi;
import cn.xk.xcode.entity.dto.CheckCodeVerifyReqDto;
import cn.xk.xcode.entity.dto.address.UpdateAddressDto;
import cn.xk.xcode.entity.dto.user.*;
import cn.xk.xcode.entity.po.TakeoutRolePo;
import cn.xk.xcode.entity.vo.TakeoutUserResultVo;
import cn.xk.xcode.enums.CheckCodeGenerateType;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.TakeoutRoleService;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.TakeoutUserPo;
import cn.xk.xcode.mapper.TakeoutUserMapper;
import cn.xk.xcode.service.TakeoutUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static cn.xk.xcode.constant.GlobalTakeoutUserConstant.*;
import static cn.xk.xcode.entity.def.TakeoutRoleTableDef.TAKEOUT_ROLE_PO;
import static cn.xk.xcode.entity.def.TakeoutUserTableDef.TAKEOUT_USER_PO;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.CHECK_CODE_IS_ERROR;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-10-29
 */
@Service
public class TakeoutUserServiceImpl extends ServiceImpl<TakeoutUserMapper, TakeoutUserPo> implements TakeoutUserService {

    @Resource
    private TakeoutRoleService takeoutRoleService;

    @Resource
    private CheckCodeClientApi checkCodeClientApi;

    @Override
    public TakeoutUserResultVo getTakeoutUser(Long userId) {
        TakeoutUserPo takeoutUserPo = this.getById(userId);
        if (Objects.isNull(takeoutUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        TakeoutUserResultVo userResultVo = BeanUtil.toBean(takeoutUserPo, TakeoutUserResultVo.class);
        userResultVo.setRoleName(takeoutRoleService.getOne(TAKEOUT_ROLE_PO.ID.eq(userResultVo.getRoleId())).getName());
        return userResultVo;
    }

    @Override
    public List<TakeoutUserResultVo> getTakeoutUserList(Collection<Long> ids) {
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        List<TakeoutUserPo> takeoutUserPoList = this.list(TAKEOUT_USER_PO.ID.in(ids));
        if (takeoutUserPoList.isEmpty()) {
            return Collections.emptyList();
        }
        List<TakeoutUserResultVo> takeoutUserResultVos = new ArrayList<>();
        takeoutUserPoList.forEach(v -> {
            TakeoutUserResultVo takeoutUserResultVo = BeanUtil.toBean(v, TakeoutUserResultVo.class);
            takeoutUserResultVo.setRoleName(takeoutRoleService.getOne(TAKEOUT_ROLE_PO.ID.eq(takeoutUserResultVo.getRoleId())).getName());
            takeoutUserResultVos.add(takeoutUserResultVo);
        });
        return takeoutUserResultVos;
    }

    @Override
    public List<TakeoutUserResultVo> getUserListByName(String name) {
        List<TakeoutUserPo> takeoutUserPoList = this.list(TAKEOUT_USER_PO.NAME.like(name));
        if (takeoutUserPoList.isEmpty()) {
            return Collections.emptyList();
        }
        List<TakeoutUserResultVo> takeoutUserResultVos = new ArrayList<>();
        takeoutUserPoList.forEach(v -> {
            TakeoutUserResultVo takeoutUserResultVo = BeanUtil.toBean(v, TakeoutUserResultVo.class);
            takeoutUserResultVo.setRoleName(takeoutRoleService.getOne(TAKEOUT_ROLE_PO.ID.eq(takeoutUserResultVo.getRoleId())).getName());
            takeoutUserResultVos.add(takeoutUserResultVo);
        });
        return takeoutUserResultVos;
    }

    @Override
    public TakeoutUserResultVo getUserByMobile(String mobile) {
        TakeoutUserPo takeoutUserPo = this.getOne(TAKEOUT_USER_PO.MOBILE.eq(mobile));
        if (Objects.nonNull(takeoutUserPo)) {
            TakeoutUserResultVo takeoutUserResultVo = BeanUtil.toBean(takeoutUserPo, TakeoutUserResultVo.class);
            takeoutUserResultVo.setRoleName(takeoutRoleService.getOne(TAKEOUT_ROLE_PO.ID.eq(takeoutUserResultVo.getRoleId())).getName());
            return takeoutUserResultVo;
        }
        return null;
    }

    @Override
    public Boolean addTakeoutUser(AddUserDto addUserDto) {
        TakeoutUserResultVo userResultVo = getUserByMobile(addUserDto.getMobile());
        if (Objects.nonNull(userResultVo)) {
            ExceptionUtil.castServiceException(MOBILE_HAS_USED, addUserDto.getMobile());
        }
        TakeoutUserPo takeoutUserPo = BeanUtil.toBean(addUserDto, TakeoutUserPo.class);
        takeoutUserPo.setPassword(SaSecureUtil.md5(addUserDto.getMobile()));
        return this.save(takeoutUserPo);
    }

    @Override
    public Boolean updateUserMsg(UpdateAddressDto updateAddressDto) {
        TakeoutUserPo takeoutUserPo = BeanUtil.toBean(updateAddressDto, TakeoutUserPo.class);
        return this.updateById(takeoutUserPo);
    }

    @Override
    public Boolean updatePassword(UpdatePasswordDto updatePasswordDto) {
        String oldPassword = updatePasswordDto.getOldPassword();
        String newPassword = updatePasswordDto.getNewPassword();
        Long id = updatePasswordDto.getId();
        TakeoutUserPo takeoutUserPo = this.getById(id);
        if (Objects.isNull(takeoutUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        if (!takeoutUserPo.getPassword().equals(oldPassword)) {
            ExceptionUtil.castServiceException(ORIGINAL_PASSWORD_ERROR);
        }
        takeoutUserPo.setPassword(newPassword);
        return this.updateById(takeoutUserPo);
    }

    @Override
    public Boolean updateRole(UpdateUserRoleDto updateUserRoleDto) {
        TakeoutUserPo takeoutUserPo = this.getById(updateUserRoleDto.getUserId());
        if (Objects.isNull(takeoutUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        TakeoutRolePo takeoutRolePo = takeoutRoleService.getById(updateUserRoleDto.getRoleId());
        if (Objects.isNull(takeoutRolePo)) {
            ExceptionUtil.castServiceException(ROLE_NOT_EXISTS);
        }
        takeoutUserPo.setRoleId(updateUserRoleDto.getRoleId());
        return this.updateById(takeoutUserPo);
    }

    @Override
    public Boolean updateStatus(UpdateUserStatusDto updateUserStatusDto) {
        TakeoutUserPo takeoutUserPo = this.getById(updateUserStatusDto.getId());
        if (Objects.isNull(takeoutUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        takeoutUserPo.setStatus(updateUserStatusDto.getStatus());
        return this.updateById(takeoutUserPo);
    }

    @Override
    public Boolean updateMobile(UpdateUserMobileDto updateUserMobileDto) {
        TakeoutUserResultVo userResultVo = getUserByMobile(updateUserMobileDto.getNewMobile());
        if (Objects.nonNull(userResultVo)) {
            ExceptionUtil.castServiceException(MOBILE_HAS_USED, updateUserMobileDto.getNewMobile());
        }
        CommonResult<Boolean> commonResult = checkCodeClientApi.verifyCode(CheckCodeVerifyReqDto.builder()
                .mobile(updateUserMobileDto.getNewMobile())
                .code(updateUserMobileDto.getCode())
                .type(CheckCodeGenerateType.MOBILE.getCode())
                .build());
        if (!CommonResult.isSuccess(commonResult)) {
            ExceptionUtil.castServiceException0(commonResult.getCode(), commonResult.getMsg());
        }
        TakeoutUserPo takeoutUserPo = this.getById(updateUserMobileDto.getId());
        if (Objects.isNull(takeoutUserPo)) {
            ExceptionUtil.castServiceException(USER_NOT_EXISTS);
        }
        takeoutUserPo.setMobile(updateUserMobileDto.getNewMobile());
        return this.updateById(takeoutUserPo);
    }
}
