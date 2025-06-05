package cn.xk.xcode.handler;

import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.utils.object.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.LOGIN_TYPE_HANDLER_NOT_EXISTS;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.LOGIN_TYPE_HANDLER_REPEATED;

/**
 * @Author xuk
 * @Date 2025/5/30 15:53
 * @Version 1.0.0
 * @Description LoginHandlerHolder
 **/
@Component
public class LoginHandlerHolder {

    private final Map<String, AbstractLoginHandler> loginHandlerMap = new HashMap<>();

    @Autowired
    public LoginHandlerHolder(List<AbstractLoginHandler> abstractLoginHandlerList) {
        for (AbstractLoginHandler abstractLoginHandler : abstractLoginHandlerList) {
            if (loginHandlerMap.containsKey(abstractLoginHandler.loginType())) {
                throw new ServerException(LOGIN_TYPE_HANDLER_REPEATED, abstractLoginHandler.loginType());
            }
            loginHandlerMap.put(abstractLoginHandler.loginType(), abstractLoginHandler);
        }
    }

    public AbstractLoginHandler routeLoginHandler(String loginType) {
        AbstractLoginHandler abstractLoginHandler = loginHandlerMap.get(loginType);
        if (ObjectUtil.isNull(abstractLoginHandler)){
            throw new ServerException(LOGIN_TYPE_HANDLER_NOT_EXISTS, loginType);
        }
        return abstractLoginHandler;
    }

}
