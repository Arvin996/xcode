package cn.xk.xcode.service.impl.auth;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.xk.xcode.client.CheckCodeClientApi;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.dto.role.QueryRoleDto;
import cn.xk.xcode.entity.dto.user.UserLoginDto;
import cn.xk.xcode.entity.po.TakeoutUserPo;
import cn.xk.xcode.entity.vo.role.RoleResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.TakeoutRoleService;
import cn.xk.xcode.service.TakeoutUserService;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import cn.xk.xcode.utils.collections.CollectionUtil;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static cn.xk.xcode.constant.GlobalTakeoutUserConstant.ACCOUNT_IS_DISABLED;
import static cn.xk.xcode.entity.def.TakeoutRoleTableDef.TAKEOUT_ROLE_PO;

/**
 * @Author xuk
 * @Date 2024/10/30 15:59
 * @Version 1.0.0
 * @Description AbstractTakeoutUserLoginHandler
 **/
@RequiredArgsConstructor
public abstract class AbstractTakeoutUserLoginHandler {

    protected final CheckCodeClientApi checkCodeClientApi;
    protected final TakeoutUserService takeoutUserService;
    protected final TakeoutRoleService takeoutRoleService;

    public LoginVO doLogin(UserLoginDto userLoginDto){
        TakeoutUserPo takeoutUserPo = validateLogin(userLoginDto);
        if (CommonStatusEnum.isDisable(takeoutUserPo.getStatus().toString())){
            ExceptionUtil.castServiceException(ACCOUNT_IS_DISABLED);
        }
        SaLoginModel saLoginModel = bulidSaLoginModel(takeoutUserPo, userLoginDto);
        LoginVO loginVO = createLoginVO(takeoutUserPo);
        LoginUser loginUser = buildLoginUser(takeoutUserPo);
        SaTokenLoginUtils.doLogin(loginUser, saLoginModel);
        return loginVO;
    }

    private LoginUser buildLoginUser(TakeoutUserPo takeoutUserPo) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(takeoutUserPo.getId().toString());
        loginUser.setRoles(CollectionUtil.createSingleSet(takeoutRoleService.getOne(TAKEOUT_ROLE_PO.ID.eq(takeoutUserPo.getRoleId())).getName()));
        List<RoleResultVo> roleList = takeoutRoleService.getRoleList(new QueryRoleDto().setId(takeoutUserPo.getRoleId()));
        if (roleList.isEmpty()){
            loginUser.setPermissions(CollectionUtil.createEmptySet());
        }else {
            loginUser.setPermissions(CollectionUtil.convertSet(roleList.get(0).getPermissionList(), Function.identity()));
        }
        return loginUser;
    }

    private LoginVO createLoginVO(Object userInfo) {
        LoginVO loginVo = new LoginVO();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setUserInfo(userInfo);
        return loginVo;
    }

    private SaLoginModel bulidSaLoginModel(TakeoutUserPo takeoutUserPo, UserLoginDto userLoginDto) {
        SaLoginModel saLoginModel = SaLoginModel.create();
        saLoginModel.setDevice(userLoginDto.getLoginDevice());
        saLoginModel.setExtraData(createExtraData(takeoutUserPo, userLoginDto));
        return saLoginModel;
    }

    public Map<String, Object> createExtraData(TakeoutUserPo takeoutUserPo, UserLoginDto userLoginDto) {
        Map<String, Object> extraData = new HashMap<>();
        extraData.put("loginId", takeoutUserPo.getId());
        extraData.put("loginType", userLoginDto.getLoginType());
        return extraData;
    }

    public abstract TakeoutUserPo validateLogin(UserLoginDto userLoginDto);
}
