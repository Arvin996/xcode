package cn.xk.xcode.service.auth;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.po.SystemRolePo;
import cn.xk.xcode.entity.po.SystemUserPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.AbstractLoginHandler;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.SystemRoleApiService;
import cn.xk.xcode.service.SystemRoleService;
import cn.xk.xcode.service.SystemUserService;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.Objects;

import static cn.xk.xcode.config.DistributionCardSystemErrorCode.ACCOUNT_IS_DISABLED;
import static cn.xk.xcode.config.DistributionCardSystemErrorCode.USERNAME_OR_PASSWORD_ERROR;
import static cn.xk.xcode.entity.def.SystemUserTableDef.SYSTEM_USER_PO;

@Service(AbstractLoginHandler.LOGIN_BASE + "_" + "password")
public class PasswordAuthImpl extends AbstractLoginHandler {

    @Resource
    private SystemUserService systemUserService;

    @Resource
    private SystemRoleService systemRoleService;

    @Resource
    private SystemRoleApiService systemRoleApiService;

    @Override
    public LoginVO handlerLogin(LoginInfoDto loginInfoDto) {
        //
        String username = loginInfoDto.getUsername();
        String password = loginInfoDto.getPassword();
        SystemUserPo systemUserPo = systemUserService.getOne(SYSTEM_USER_PO.USERNAME.eq(username));
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

    private LoginUser buildLoginUser(SystemUserPo systemUserPo) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername(systemUserPo.getUsername());
        SystemRolePo systemRolePo = systemRoleService.getById(systemUserPo.getRoleId());
        loginUser.setRoles(Collections.singleton(systemRolePo.getCode()));
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select();
        loginUser.setPermissions();
        return loginUser;
    }

}