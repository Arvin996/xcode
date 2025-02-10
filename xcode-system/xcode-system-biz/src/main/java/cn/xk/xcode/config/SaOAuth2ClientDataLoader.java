package cn.xk.xcode.config;


import cn.dev33.satoken.oauth2.data.loader.SaOAuth2DataLoader;
import cn.dev33.satoken.oauth2.data.model.loader.SaClientModel;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.po.ClientPo;
import cn.xk.xcode.service.ClientService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/22 20:01
 * @description
 */
@Configuration
public class SaOAuth2ClientDataLoader implements SaOAuth2DataLoader {

    @Resource
    private ClientService clientService;

    // 根据 id 获取 Client 信息
    @Override
    public SaClientModel getClientModel(String clientId) {
        ClientPo clientPo = clientService.getById(clientId);
        if (Objects.isNull(clientPo) || CommonStatusEnum.isDisable(clientPo.getStatus())){
            return null;
        }
        SaClientModel saClientModel = new SaClientModel();
        saClientModel.setClientId(clientPo.getClientId());
        saClientModel.setClientSecret(clientPo.getClientSecret());
        saClientModel.setContractScopes(clientPo.getScopes());
        saClientModel.setAllowRedirectUris(clientPo.getRedirectUris());
        saClientModel.setAllowGrantTypes(clientPo.getGrantType());
        saClientModel.setSubjectId(clientPo.getSubjectId());
        saClientModel.setAccessTokenTimeout(clientPo.getAccessTokenValidity());
        saClientModel.setRefreshTokenTimeout(clientPo.getRefreshTokenValidity());
        return saClientModel;
    }

    // 根据ClientId 和 LoginId 获取openid
    @Override
    public String getOpenid(String clientId, Object loginId) {
        // 此为模拟数据，真实环境需要从数据库查询 todo 暂时不做openId 后期要存在数据库
        /**
         * 表结构参考如下：
         * id：数据id，主键。
         * client_id：应用id。
         * user_id：用户账号id。
         * openid：对应的 openid 值，随机字符串。
         * create_time：数据创建时间。
         * xxx：其它需要扩展的字段。
         */
        return SaOAuth2DataLoader.super.getOpenid(clientId, loginId);
    }
}
