package cn.xk.xcode.service.impl.auth;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.entity.po.ClientPo;
import cn.xk.xcode.entity.po.UserPo;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.AbstractLoginHandler;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.ClientService;
import cn.xk.xcode.service.UserService;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static cn.xk.xcode.enums.GlobalStatusEnum.DISABLE;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.*;

/**
 * @Author xuk
 * @Date 2024/6/24 11:15
 * @Version 1.0
 * @Description PasswordAuthImpl
 */
@Service(AbstractLoginHandler.LOGIN_BASE + "_" + "password")
public class PasswordAuthImpl extends AbstractLoginHandler {
    @Resource
    private UserService userService;

    @Resource
    private ClientService clientService;

    @Override
    public LoginVO handlerLogin(LoginInfoDto loginInfoDto) {
        //
        String username = loginInfoDto.getUsername();
        String password = loginInfoDto.getPassword();
        CommonResult<UserPo> userPoCommonResult = userService.queryByUsername(username);
        if (!CommonResult.isSuccess(userPoCommonResult)) {
            throw new ServiceException(userPoCommonResult.getCode(), userPoCommonResult.getMsg());
        }
        ClientPo clientPo = (ClientPo) validateClient(loginInfoDto);
        UserPo userPo = userPoCommonResult.getData();
        if (!SaSecureUtil.md5(password).equals(userPo.getPassword())) {
            throw new ServiceException(INVALID_USERNAME_OR_PASSWORD);
        }
        if (userPo.getStatus().equals(DISABLE.getValue())) {
            throw new ServiceException(ACCOUNT_NOT_ENABLE);
        }
        SaLoginModel saLoginModel = bulidSaLoginModel(clientPo.getClientId(), loginInfoDto);
        LoginVO loginVO = createLoginVO(userPo);
        LoginUser loginUser = userService.buildLoginUser(userPo);
        SaTokenLoginUtils.doLogin(loginUser, saLoginModel);
        return loginVO;
    }

    @Override
    public Object validateClient(LoginInfoDto loginInfoDto) {
        ClientPo clientPo = clientService.getById(loginInfoDto.getClientId());
        if (ObjectUtil.isNull(clientPo)) {
            throw new ServiceException(INVALID_CLIENT);
        }
        return clientPo;
    }

}
