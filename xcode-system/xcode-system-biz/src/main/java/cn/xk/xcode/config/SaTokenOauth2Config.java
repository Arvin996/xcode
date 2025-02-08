//package cn.xk.xcode.config;
//
//import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;
//import cn.dev33.satoken.oauth2.model.SaClientModel;
//import cn.xk.xcode.entity.po.ClientPo;
//import cn.xk.xcode.service.ClientService;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Objects;
//
///**
// * @author xukai
// * @version 1.0
// * @date 2024/6/22 20:01
// * @description
// */
//@Component
//public class SaTokenOauth2Config extends SaOAuth2Template {
//
//    @Resource
//    private ClientService clientService;
//
//    // 根据 id 获取 Client 信息
//    @Override
//    public SaClientModel getClientModel(String clientId) {
//        ClientPo clientPo = clientService.getById(clientId);
//        if (Objects.isNull(clientPo)){
//            return null;
//        }
//        SaClientModel saClientModel = new SaClientModel();
//        saClientModel.setClientId(clientPo.getClientId());
//        saClientModel.setClientSecret(clientPo.getClientSecret());
//        saClientModel.setContractScope(clientPo.getScopes());
//        saClientModel.setAllowUrl();
//        saClientModel.setIsCode();
//        saClientModel.setIsImplicit();
//        saClientModel.setIsPassword();
//        saClientModel.setIsClient();
//        saClientModel.setIsAutoMode();
//        saClientModel.setIsNewRefresh();
//        saClientModel.setAccessTokenTimeout();
//        saClientModel.setRefreshTokenTimeout();
//        return saClientModel;
//    }
//
//    // 根据ClientId 和 LoginId 获取openid
//    @Override
//    public String getOpenid(String clientId, Object loginId) {
//        // 此为模拟数据，真实环境需要从数据库查询
//        return "gr_SwoIN0MC1ewxHX_vfCW3BothWDZMMtx__";
//    }
//}
