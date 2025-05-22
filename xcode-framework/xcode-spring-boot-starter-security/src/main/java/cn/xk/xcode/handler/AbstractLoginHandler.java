package cn.xk.xcode.handler;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginVO;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/6/24 10:58
 * @Version 1.0
 * @Description AbstractLoginHandler
 */
public abstract class AbstractLoginHandler {

    public static final String LOGIN_BASE = "AuthStrategy";

    public abstract LoginVO handlerLogin(LoginInfoDto loginInfoDto);

    public LoginVO Login(LoginInfoDto loginInfoDto) {
        validateClient(loginInfoDto);
        return handlerLogin(loginInfoDto);
    }

    public abstract Object validateClient(LoginInfoDto loginInfoDto);

    public SaLoginModel bulidSaLoginModel(String clientId, LoginInfoDto loginInfoDto){
        SaLoginModel saLoginModel = SaLoginModel.create();
        if (StrUtil.isNotBlank(loginInfoDto.getLoginDevType())){
            saLoginModel.setDevice(loginInfoDto.getLoginDevType());
        }
        saLoginModel.setExtraData(createExtraData(clientId, loginInfoDto.getUsername(), loginInfoDto.getGrantType()));
        return saLoginModel;
    }

    public Map<String, Object> createExtraData(String clientId, String username, String loginType) {
        Map<String, Object> extraData = new HashMap<>();
        extraData.put("clientId", clientId);
        extraData.put("loginId", username);
        extraData.put("loginType", loginType);
        return extraData;
    }

    public abstract LoginVO createLoginVO(Object userInfo);
}
