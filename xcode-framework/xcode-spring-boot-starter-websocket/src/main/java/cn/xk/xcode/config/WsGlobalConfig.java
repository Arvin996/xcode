package cn.xk.xcode.config;

import cn.xk.xcode.handler.WebSocketMessageHandler;
import cn.xk.xcode.interceptor.CheckWsLoginHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.annotation.Resource;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/7/2 17:53
 * @description
 */
@Configuration
public class WsGlobalConfig implements WebSocketConfigurer {

    @Resource
    private WebSocketMessageHandler webSocketMessageHandler;

    @Resource
    private CheckWsLoginHandshakeInterceptor checkWsLoginHandshakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketMessageHandler, "/ws")
                .setAllowedOrigins("*")
                .addInterceptors(checkWsLoginHandshakeInterceptor);
    }
}
