package cn.xk.xcode.interceptor;

import cn.dev33.satoken.jwt.SaJwtUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.jwt.JWT;
import cn.xk.xcode.core.CheckLoginHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/7/2 13:16
 * @Version 1.0
 * @Description CheckWsLoginHandshakeInterceptor
 */
@Slf4j
@SuppressWarnings("all")
@Component
public class CheckWsLoginHandshakeInterceptor implements HandshakeInterceptor {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Lazy
    @Resource
    private CheckLoginHandler checkLoginHandler;

    //  public static final String TOKEN_SESSION = "Authorization:login:token-session:{}";
    public static final String SEC_WEBSOCKET_TOKEN_HEADER = "sec-websocket-token";

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
        ServletServerHttpResponse serverHttpResponse = (ServletServerHttpResponse) response;
        String token = serverHttpRequest.getServletRequest().getHeader(SEC_WEBSOCKET_TOKEN_HEADER);
        return checkLoginHandler.checkLogin(token, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
