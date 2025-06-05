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
 * @Description WxAuthImpl 考虑新建一个普通用户作为三方登录的默认角色
 */
@Slf4j
@Service
public class WxAuthImpl extends AbstractLoginHandler {
    @Resource
    private UserService userService;

    @Resource
    private ClientService clientService;

    @Resource
    private ThirdUserService thirdUserService;

    @Override
    public LoginVO handlerLogin(LoginInfoDto loginInfoDto) {
        ClientPo clientPo = (ClientPo) validateClient(loginInfoDto);
        // 这里不需要校验 因为在实体类中已经校验过了
        String code = loginInfoDto.getCode();
        // 通过code拿到token
        Map<String, Object> wxOauth2AccessTokenMap = getWxOauth2AccessToken(code, clientPo);
        String openId = (String) wxOauth2AccessTokenMap.get("openid");
        String accessToken = (String) wxOauth2AccessTokenMap.get("access_token");
        Map<String, Object> wxUserInfo = getWxUserInfo(accessToken, openId);
        // 1. 通过openId 查询用户是否存在
        //   WxUserPo wxUserPo = wxUserService.getOne(WX_USER_PO.UNION_ID.eq(openId));
        ThirdUserPo thirdUserPo = thirdUserService.getOne(THIRD_USER_PO.UNION_ID.eq(openId));
        if (ObjectUtil.isNull(thirdUserPo)) {
            // 插入到数据库
            // 1. 插入到用户表 获取返回的用户id
            UserPo userPo = new UserPo();
            userPo.setNickname(wxUserInfo.get("nickname").toString());
            userPo.setUserpic(wxUserInfo.get("headimgurl").toString());
            userPo.setUsername(openId);
            userPo.setPassword(openId);
            userService.save(userPo);

            //2. 插入到三方用户表
            thirdUserPo = new ThirdUserPo();
            thirdUserPo.setUnionId(openId);
            thirdUserPo.setUserId(userPo.getId());
            thirdUserPo.setNickname(wxUserInfo.get("nickname").toString());
            thirdUserPo.setAvatar(wxUserInfo.get("headimgurl").toString());
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
    public String loginType() {
        return "wx";
    }

    private Map<String, Object> getWxOauth2AccessToken(String code, ClientPo clientPo) {
        String url = String.format(SystemConstant.WX_USER_TOKEN_URL, clientPo.getClientKey(), clientPo.getClientSecret(), code);
        log.info("开始请求微信认证服务器，得到token");
        String body = HttpRequest.post(url).execute().body();
        log.info("调用微信接口申请access_token: 返回值:{}", body);
        return JSONUtil.parseObj(body);
    }

    private Map<String, Object> getWxUserInfo(String accessToken, String openId) {
        String url = String.format(SystemConstant.WX_USER_INFO_URL, accessToken, openId);
        log.info("开始请求微信认证服务器，得到用户信息");
        String body = HttpRequest.get(url).execute().body();
        log.info("调用微信接口申请用户信息: 返回值:{}", body);
        return JSONUtil.parseObj(body);
    }

    @Override
    public LoginVO createLoginVO(Object userInfo) {
        LoginVO loginVo = new LoginVO();
        loginVo.setAccessToken(StpSystemUtil.getTokenValue());
        loginVo.setExpireIn(StpSystemUtil.getTokenTimeout());
        loginVo.setUserInfo(userInfo);
        return loginVo;
    }
}
