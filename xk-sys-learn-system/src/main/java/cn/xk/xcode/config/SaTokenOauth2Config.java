package cn.xk.xcode.config;

import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
import cn.dev33.satoken.oauth2.model.SaClientModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/22 20:01
 * @description
 */
@Component
public class SaTokenOauth2Config extends SaOAuth2Template {

    @Value("${system.application.clientId}")
    private String sysClientId;

    @Value("${system.application.clientSecret}")
    private String clientSecret;


    // 根据 id 获取 Client 信息
    @Override
    public SaClientModel getClientModel(String clientId) {
        // 此为模拟数据，真实环境需要从数据库查询
        if(clientId.equals(sysClientId)) {
            return new SaClientModel()
                    .setClientId(sysClientId)
                    .setClientSecret(clientSecret)
                    .setAllowUrl("*")
                    .setIsCode(true)
                    .setContractScope("userinfo")
                    .setIsAutoMode(true);
        }
        //
        return null;
    }

    // 根据ClientId 和 LoginId 获取openid
    @Override
    public String getOpenid(String clientId, Object loginId) {
        // 此为模拟数据，真实环境需要从数据库查询
        return "gr_SwoIN0MC1ewxHX_vfCW3BothWDZMMtx__";
    }
}
