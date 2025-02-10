package cn.xk.xcode.controller;

import cn.dev33.satoken.oauth2.SaOAuth2Manager;
import cn.dev33.satoken.oauth2.config.SaOAuth2ServerConfig;
import cn.dev33.satoken.oauth2.processor.SaOAuth2ServerProcessor;
import cn.xk.xcode.core.StpSystemUtil;
import cn.xk.xcode.pojo.LoginInfoDto;
import cn.xk.xcode.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/22 20:02
 * @description
 */
@RestController
@Tag(name = "oauth2资源服务认证控制器")
public class SaOAuth2ServerController {

    @Resource
    private AuthService authService;

    // 模式一：Code授权码 || 模式二：隐藏式
    @RequestMapping("/oauth2/authorize")
    public Object authorize() {
        return SaOAuth2ServerProcessor.instance.authorize();
    }

    // 用户登录
    @RequestMapping("/oauth2/doLogin")
    public Object doLogin(@RequestBody LoginInfoDto loginUserDto) {
        return authService.doLogin(loginUserDto);
    }

    // 用户确认授权
    @RequestMapping("/oauth2/doConfirm")
    public Object doConfirm() {
        return SaOAuth2ServerProcessor.instance.doConfirm();
    }

    // Code 换 Access-Token || 模式三：密码式
    @RequestMapping("/oauth2/token")
    public Object token() {
        return SaOAuth2ServerProcessor.instance.token();
    }

    // Refresh-Token 刷新 Access-Token
    @RequestMapping("/oauth2/refresh")
    public Object refresh() {
        return SaOAuth2ServerProcessor.instance.refresh();
    }

    // 回收 Access-Token
    @RequestMapping("/oauth2/revoke")
    public Object revoke() {
        return SaOAuth2ServerProcessor.instance.revoke();
    }

    // 模式四：凭证式
    @RequestMapping("/oauth2/client_token")
    public Object clientToken() {
        return SaOAuth2ServerProcessor.instance.clientToken();
    }

    // Sa-Token OAuth2 定制化配置
    @Autowired
    public void configOAuth2Server(SaOAuth2ServerConfig oauth2Server) {

        // 配置：未登录时返回的View
        oauth2Server.notLoginView = () -> new ModelAndView("/template/notLogin.html");
        SaOAuth2Manager.setStpLogic(StpSystemUtil.stpLogic);
        // 配置：确认授权时返回的 view
        oauth2Server.confirmView = (clientId, scopes) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("clientId", clientId);
            map.put("scope", scopes);
            return new ModelAndView("/template/confirm.html", map);
        };
    }
}
