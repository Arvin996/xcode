package cn.xk.xcode.controller;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.oauth2.config.SaOAuth2Config;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/22 20:02
 * @description
 */
@RestController
@Tag(name = "oauth2服务端控制器")
@RequestMapping("/oauth2")
public class SaOAuth2ServerController {

    @Operation(summary = "Sa-OAuth2 服务端相关接口处理")
    // 处理所有OAuth相关请求
    @RequestMapping("/**")
    public Object request() {
        System.out.println("------- 进入请求: " + SaHolder.getRequest().getUrl());
        return SaOAuth2Handle.serverRequest();
    }

    // Sa-OAuth2 定制化配置
    @Autowired
    public void setSaOAuth2Config(SaOAuth2Config cfg) {
        // 配置：未登录时返回的View
        cfg.setNotLoginView(() -> CommonResult.error("500", "未登录,请先登录"))
                // 配置：确认授权时返回的View
                .setConfirmView((clientId, scope) -> "<p>应用 " + clientId + " 请求授权：" + scope + "</p>"
                        + "<p>请确认：<a href='/oauth2/doConfirm?client_id=" + clientId + "&scope=" + scope + "' target='_blank'> 确认授权 </a></p>"
                        + "<p>确认之后刷新页面</p>")
        ;
    }
}
