package cn.xk.xcode.handler;

import cn.dev33.satoken.oauth2.data.model.AccessTokenModel;
import cn.dev33.satoken.oauth2.data.model.ClientTokenModel;
import cn.dev33.satoken.oauth2.scope.handler.SaOAuth2ScopeHandlerInterface;
import cn.xk.xcode.core.annotation.Handler;
import cn.xk.xcode.entity.po.UserPo;
import cn.xk.xcode.enums.Oauth2Scopes;
import cn.xk.xcode.service.UserService;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/2/10 10:24
 * @Version 1.0.0
 * @Description UserinfoScopeHandler
 **/
@Handler
public class UserinfoScopeHandler implements SaOAuth2ScopeHandlerInterface {

    @Resource
    private UserService userService;

    @Override
    public String getHandlerScope() {
        return Oauth2Scopes.USER_INFO.getValue();
    }

    @Override
    public void workAccessToken(AccessTokenModel accessTokenModel) {
        UserPo userPo = userService.getById((Long) accessTokenModel.getLoginId());
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("userId", userPo.getId());
        map.put("nickname", userPo.getNickname());
        map.put("avatar", userPo.getUserpic());
        map.put("userName", userPo.getUsername());
        accessTokenModel.extraData.putAll(map);
    }

    @Override
    public void workClientToken(ClientTokenModel clientTokenModel) {

    }
}
