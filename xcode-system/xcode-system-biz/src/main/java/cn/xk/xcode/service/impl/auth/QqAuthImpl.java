package cn.xk.xcode.service.impl.auth;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import cn.xk.xcode.constant.SystemConstant;
import cn.xk.xcode.core.StpSystemUtil;
import cn.xk.xcode.entity.po.ClientPo;
import cn.xk.xcode.entity.po.ThirdUserPo;
import cn.xk.xcode.entity.po.UserPo;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.handler.AbstractLoginHandler;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.pojo.LoginUser;
import cn.xk.xcode.pojo.LoginVO;
import cn.xk.xcode.service.ClientService;
import cn.xk.xcode.service.ThirdUserService;
import cn.xk.xcode.service.UserService;
import cn.xk.xcode.utils.SaTokenLoginUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.Map;

import static cn.xk.xcode.entity.def.ThirdUserTableDef.THIRD_USER_PO;
import static cn.xk.xcode.enums.GlobalStatusEnum.DISABLE;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.ACCOUNT_NOT_ENABLE;
import static cn.xk.xcode.exception.GlobalErrorCodeConstants.INVALID_CLIENT;

/**
 * @Author xuk
 * @Date 2024/6/24 11:16
 * @Version 1.0
 * @Description QqAuthImpl oauth2.0登录
 */
@Slf4j
@Service(AbstractLoginHandler.LOGIN_BASE + "_" + "qq")
public class QqAuthImpl extends AbstractLoginHandler
{
    @Resource
    private UserService userService;

    @Resource
    private ClientService clientService;

    @Resource
    private ThirdUserService thirdUserService;

    @Override
    public LoginVO handlerLogin(LoginInfoDto loginInfoDto) {
        String code = loginInfoDto.getCode();
        ClientPo clientPo = (ClientPo) validateClient(loginInfoDto);
        // 获取用户token
        Map<String, Object> qqOauth2AccessToken = getQqOauth2AccessToken(code, clientPo);
        String token = qqOauth2AccessToken.get("access_token").toString();
        String openId = getQqOpenId(token);
        Map<String, Object> qqUserInfo = getQqUserInfo(clientPo, openId, token);
        ThirdUserPo thirdUserPo = thirdUserService.getOne(THIRD_USER_PO.UNION_ID.eq(openId));
        if (ObjectUtil.isNull(thirdUserPo)) {
            // 插入到数据库
            // 1. 插入到用户表 获取返回的用户id
            UserPo userPo = new UserPo();
            userPo.setNickname(qqUserInfo.get("nickname").toString());
            userPo.setUserpic(qqUserInfo.get("figureurl_qq_2").toString());
            userPo.setUsername(openId);
            userPo.setPassword(openId);
            userService.save(userPo);

            //2. 插入到三方用户表
            thirdUserPo = new ThirdUserPo();
            thirdUserPo.setUnionId(openId);
            thirdUserPo.setUserId(userPo.getId());
            thirdUserPo.setNickname(qqUserInfo.get("nickname").toString());
            thirdUserPo.setAvatar(qqUserInfo.get("figureurl_qq_2").toString());
            thirdUserService.save(thirdUserPo);

        }
        UserPo userPo = userService.getById(thirdUserPo.getUserId());
        if (userPo.getStatus().equals(DISABLE.getValue())) {
            throw new ServiceException(ACCOUNT_NOT_ENABLE);
        }
        loginInfoDto.setUsername(userPo.getUsername());
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

    @Override
    public LoginVO createLoginVO(Object userInfo) {
        LoginVO loginVo = new LoginVO();
        loginVo.setAccessToken(StpSystemUtil.getTokenValue());
        loginVo.setExpireIn(StpSystemUtil.getTokenTimeout());
        loginVo.setUserInfo(userInfo);
        return loginVo;
    }

    private Map<String, Object> getQqOauth2AccessToken(String code, ClientPo clientPo){
        String url = String.format(SystemConstant.QQ_USER_TOKEN_URL, clientPo.getClientKey(), clientPo.getClientSecret(), code, "www.baidu.com");
        log.info("开始请求qq认证服务器，得到token");
        String body = HttpRequest.get(url).execute().body();
        Map<String, Object> map = JSONUtil.parseObj(body);
        log.info("qq认证服务器返回信息{}", body);
        return map;
    }

    private String getQqOpenId(String token){
        String url = String.format(SystemConstant.QQ_USER_OPEN_ID, token);
        log.info("开始请求qq认证服务器，得到openId");
        String body = HttpRequest.get(url).execute().body();
        Map<String, Object> map = JSONUtil.parseObj(body);
        log.info("qq认证服务器返回信息{}", body);
        return (String) map.get("openid");
    }

    private Map<String, Object> getQqUserInfo(ClientPo clientPo,  String openId, String token){
        String url = String.format(SystemConstant.QQ_USER_INFO_URL, token, clientPo.getClientKey(), openId);
        log.info("开始请求qq认证服务器，得到用户信息");
        String body = HttpRequest.get(url).execute().body();
        Map<String, Object> map = JSONUtil.parseObj(body);
        log.info("qq认证服务器返回信息{}", body);
        return map;
    }


}
