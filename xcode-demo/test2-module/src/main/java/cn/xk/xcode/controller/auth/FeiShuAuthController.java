package cn.xk.xcode.controller.auth;

import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.request.AuthFeishuRequest;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthScopeUtils;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author xuk
 * @Date 2025/6/9 17:12
 * @Version 1.0.0
 * @Description FeiShuAuthController
 **/
@RestController
@RequestMapping("/auth/feishu")
public class FeiShuAuthController {

    @RequestMapping("/render")
    public void renderAuth(HttpServletResponse response) throws IOException {
        AuthRequest authRequest = getAuthRequest();
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    @RequestMapping("/callback")
    public Object login(AuthCallback callback) {
        AuthRequest authRequest = getAuthRequest();
        return authRequest.login(callback);
    }

    private AuthRequest getAuthRequest() {
        return new AuthFeishuRequest(AuthConfig.builder()
                .clientId("cli_a8aef75a2cb9d00c")
                .clientSecret("zaLCjC8pErViW62SNykuWgIe2cFeVg8b")
                .scopes(AuthScopeUtils.getScopes())
                .redirectUri("http://xxkk1118.w1.luyouxia.net/auth/feishu/callback")
                .build());
    }
}
