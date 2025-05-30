package cn.xk.xcode.service.auth;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.core.StpSystemUtil;
import cn.xk.xcode.entity.dto.menu.QueryMenuDto;
import cn.xk.xcode.entity.po.SystemRolePo;
import cn.xk.xcode.entity.po.SystemUserPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.AbstractLoginHandler;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.SystemApiService;
import cn.xk.xcode.service.SystemMenuService;
import cn.xk.xcode.service.SystemRoleService;
import cn.xk.xcode.service.SystemUserService;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;

import static cn.xk.xcode.config.DistributionCardSystemErrorCode.*;
import static cn.xk.xcode.entity.def.SystemApiTableDef.SYSTEM_API_PO;
import static cn.xk.xcode.entity.def.SystemRoleApiTableDef.SYSTEM_ROLE_API_PO;
import static cn.xk.xcode.entity.def.SystemUserTableDef.SYSTEM_USER_PO;

/**
 * @Author xuk
 * @Date 2025/5/26 13:53
 * @Version 1.0.0
 * @Description EmailAuthImpl
 **/
@Service
public class EmailAuthImpl  extends AbstractLoginHandler{

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private SystemRoleService systemRoleService;

    @Resource
    private SystemMenuService systemMenuService;

    @Resource
    private SystemApiService systemApiService;

    @Override
    public LoginVO handlerLogin(LoginInfoDto loginInfoDto) {
        String email = loginInfoDto.getEmail();
        String password = loginInfoDto.getPassword();
        if (StrUtil.isBlank(email)){
            ExceptionUtil.castServerException(LOGIN_EMAIL_MUST_NOT_EMPTY);
        }
        SystemUserPo systemUserPo = systemUserService.getOne(SYSTEM_USER_PO.EMAIL.eq(email));
        if (Objects.isNull(systemUserPo)) {
            ExceptionUtil.castServiceException(USERNAME_OR_PASSWORD_ERROR);
        }
        if (!password.equals(systemUserPo.getPassword())) {
            throw new ServiceException(USERNAME_OR_PASSWORD_ERROR);
        }
        if (CommonStatusEnum.isDisable(systemUserPo.getStatus())) {
            throw new ServiceException(ACCOUNT_IS_DISABLED);
        }
        SaLoginModel saLoginModel = bulidSaLoginModel("xcode-dc-system", loginInfoDto);
        LoginVO loginVO = createLoginVO(systemUserPo);
        LoginUser loginUser = buildLoginUser(systemUserPo);
        SaTokenLoginUtils.doLogin(loginUser, saLoginModel);
        return loginVO;
    }

    @Override
    public Object validateClient(LoginInfoDto loginInfoDto) {
        return null;
    }

    @Override
    public String loginType() {
        return "email";
    }

    @Override
    public LoginVO createLoginVO(Object userInfo) {
        LoginVO loginVo = new LoginVO();
        loginVo.setAccessToken(StpSystemUtil.getTokenValue());
        loginVo.setExpireIn(StpSystemUtil.getTokenTimeout());
        loginVo.setUserInfo(userInfo);
        return loginVo;
    }

    private LoginUser buildLoginUser(SystemUserPo systemUserPo) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(systemUserPo.getUsername());
        SystemRolePo systemRolePo = systemRoleService.getById(systemUserPo.getRoleId());
        loginUser.setRoles(Collections.singleton(systemRolePo.getCode()));
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(SYSTEM_API_PO.API_CODE)
                .from(SYSTEM_ROLE_API_PO)
                .leftJoin(SYSTEM_API_PO)
                .on(SYSTEM_ROLE_API_PO.API_ID.eq(SYSTEM_API_PO.ID))
                .where(SYSTEM_ROLE_API_PO.ROLE_ID.eq(systemRolePo.getId()));
        loginUser.setPermissions(new HashSet<>(systemApiService.listAs(queryWrapper, String.class)));
        // 获取菜单
        loginUser.setMenus(systemMenuService.queryAllMenu(QueryMenuDto.builder().roleId(systemRolePo.getId()).build()));
        return loginUser;
    }
}
