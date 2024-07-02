package cn.xk.xcode.interceptor;

import cn.dev33.satoken.jwt.SaJwtUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.jwt.JWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
@Component
public class CheckWsLoginHandshakeInterceptor implements HandshakeInterceptor {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public static final String TOKEN_SESSION = "Authorization:login:token-session:{}";
    public static final String SEC_WEBSOCKET_PROTOCOL_HEADER = "sec-websocket-protocol";

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest serverHttpRequest = (ServletServerHttpRequest) request;
        ServletServerHttpResponse serverHttpResponse = (ServletServerHttpResponse) response;
        String authorization = serverHttpRequest.getServletRequest().getHeader(SEC_WEBSOCKET_PROTOCOL_HEADER);
        String jwtSecretKey = StpUtil.getStpLogic().getConfigOrGlobal().getJwtSecretKey();
        if (!StringUtils.isEmpty(authorization)) {
            JWT jwt = SaJwtUtil.parseToken(authorization, "login", jwtSecretKey, false);
            if (!jwt.verify()) {
                return false;
            }
            String loginId = jwt.getPayload().getClaimsJson().get("loginId").toString();
            String key = String.format(TOKEN_SESSION, authorization);
            // 从redis 里获取用户，验证是否是有效用户,如果失败则中断连接
            if (Boolean.TRUE.equals(redisTemplate.hasKey(key))) {
                // 自定义attribute
                attributes.put("loginId", loginId);
                log.info("用户: [{}]建立连接, token:[{}]", loginId, authorization);
                serverHttpResponse.getServletResponse().setHeader(SEC_WEBSOCKET_PROTOCOL_HEADER, authorization);
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
