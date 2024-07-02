package cn.xk.xcode.handler;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import cn.xk.xcode.core.WebSocketManagerCache;
import cn.xk.xcode.core.WsRedisStatisticsAndManage;
import cn.xk.xcode.core.WsSession;
import cn.xk.xcode.core.WsState;
import cn.xk.xcode.listener.WebSocketMessageListener;
import cn.xk.xcode.message.MessageEntity;
import cn.xk.xcode.utils.object.BeanUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @Author xuk
 * @Date 2024/7/2 11:24
 * @Version 1.0
 * @Description WebSocketMessageHandler
 */
@Component
@Slf4j
public class WebSocketMessageHandler extends TextWebSocketHandler {

    @Resource
    private WsState wsState;

    @Resource
    WsRedisStatisticsAndManage wsRedisStatisticsAndManage;


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 1.1 空消息，跳过
        if (message.getPayloadLength() == 0) {
            return;
        }
        // 1.2 ping 心跳消息，直接返回 pong 消息。
        if (message.getPayloadLength() == 4 && Objects.equals(message.getPayload(), "ping")) {
            session.sendMessage(new TextMessage("pong"));
            return;
        }
        try {
            MessageEntity messageEntity = BeanUtil.toBean(message.getPayload(), MessageEntity.class);
            if (Objects.isNull(messageEntity)) {
                log.error("[handleTextMessage][session({}) message({}) 解析为空]", session.getId(), message.getPayload());
                return;
            }
            String messageType = messageEntity.getHeader().getType();
            if (StringUtils.isEmpty(messageType)) {
                log.error("[handleTextMessage][session({}) message({}) 消息类型为空]", session.getId(), message.getPayload());
                return;
            }
            // 监听器处理
            Map<String, WebSocketMessageListener> beans = SpringUtil.getBeansOfType(WebSocketMessageListener.class);
            List<WebSocketMessageListener> collect = beans.values().stream().filter(bean -> bean.getMsgType().equals(messageType)).collect(Collectors.toList());
            if (collect.isEmpty()) {
                log.error("[handleTextMessage][session({}) message({}) 消息类型({}) 没有对用的监听器处理]", session.getId(), message.getPayload(), messageType);
                return;
            }
            if (collect.size() > 1) {
                log.error("[handleTextMessage][session({}) message({}) 消息类型({}) 不允许有多个监听器]", session.getId(), message.getPayload(), messageType);
                return;
            }
            collect.get(0).onMessage(session, messageEntity);
        } catch (Exception e) {
            log.error("[handleTextMessage][session({}) message({}) 消息处理异常]", session.getId(), message.getPayload(), e);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        if (!wsState.isState()) {
            log.warn("Service is shutting down. Skipping cleanup for closed connection.");
            return;
        }
        String loginId = (String) session.getAttributes().get("loginId");
        log.info("【websocket消息】连接断开，loginId:[{}]", loginId);
        if (session.isOpen()) {
            try {
                String sid = session.getId();
                wsRedisStatisticsAndManage.removeUserBySessionId(sid);
                // 注意删除顺序,先清除WSSession的Map
                WebSocketManagerCache.removeUserSession(sid);
                WebSocketManagerCache.onlineSessionMap.remove(sid);
                session.close();
            } catch (IOException e) {
                log.error("【websocket消息】连接断开异常，error: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (!wsState.isState()) {
            log.warn("Service is shutting down. Rejecting new connection.");
            session.close(CloseStatus.SERVICE_RESTARTED);
            return;
        }
        String sid = session.getId();
        String loginId = (String) session.getAttributes().get("loginId");
        WsSession wsSession = new WsSession(sid, loginId, session);

        WebSocketManagerCache.onlineSessionMap.put(sid, wsSession);
        WebSocketManagerCache.addOnlineSid(loginId, sid);
        wsRedisStatisticsAndManage.addUserToOnlineChat(sid, loginId);
        System.out.println("当前连接数:" + wsRedisStatisticsAndManage.getConnectionCount());
        System.out.println("当前在线人数: " + wsRedisStatisticsAndManage.getOnlineUserCount());
        System.out.println("当前内存中的用户: " + (wsRedisStatisticsAndManage.getAllOnlineUsernames()));
    }

    public void sendMessage(MessageEntity messageEntity) {
        List<String> loginIds = messageEntity.getToUsers();
        log.info(" 定向推送。推送用户范围:{}, message: {}", loginIds, JSONUtil.toJsonStr(messageEntity));
        for (String loginId : loginIds) {
            // 验证当前内存中【用户】是否存在
            boolean existsUsername = WebSocketManagerCache.onlineUserSessionIdMap.containsKey(loginId);
            if (existsUsername) {
                List<String> notifyUserSids = WebSocketManagerCache.onlineUserSessionIdMap.get(loginId);
                for (String notifyUserSid : notifyUserSids) {
                    // 验证当前内存中【session】是否存在
                    boolean existsUserSession = WebSocketManagerCache.onlineSessionMap.containsKey(notifyUserSid);
                    if (existsUserSession) {
                        WsSession wsSession = WebSocketManagerCache.onlineSessionMap.get(notifyUserSid);
                        try {
                            wsSession.getSession().sendMessage(new TextMessage(JSONUtil.toJsonStr(messageEntity.getData())));
                        } catch (IOException e) {
                            log.error(" websocket发送消息失败。message: {}。用户:{}推送失败", JSONUtil.toJsonStr(messageEntity), loginId);
                        }
                    } else {
                        log.info(" websocket定向推送。message: {}。用户:{}推送失败", JSONUtil.toJsonStr(messageEntity), loginId);
                    }
                }
            }
        }
    }

    /**
     * 根据loginId 推送消息
     */
    public void sendMessageToAllUser(MessageEntity messageEntity) {
        log.info(" 全员推送。message: {}", JSONUtil.toJsonStr(messageEntity));
        List<String> allOnlineUsernames = new ArrayList<>(WebSocketManagerCache.onlineUserSessionIdMap.keySet());
        for (String loginId : allOnlineUsernames) {
            List<String> notifyUserSids = WebSocketManagerCache.onlineUserSessionIdMap.get(loginId);
            for (String notifyUserSid : notifyUserSids) {
                boolean existsUserSession = WebSocketManagerCache.onlineSessionMap.containsKey(notifyUserSid);
                if (existsUserSession) {
                    WsSession wsSession = WebSocketManagerCache.onlineSessionMap.get(notifyUserSid);
                    try {
                        wsSession.getSession().sendMessage(new TextMessage(JSONUtil.toJsonStr(messageEntity.getData())));
                    } catch (IOException e) {
                        log.error(" websocket全员推送。message: {}。用户:{}推送失败", JSONUtil.toJsonStr(messageEntity), loginId);
                    }
                } else {
                    log.info(" websocket全员推送。message: {}。用户:{}推送失败", JSONUtil.toJsonStr(messageEntity), loginId);
                }
            }
        }
    }

    public void disconnectAll() {
        ConcurrentHashMap<String, WsSession> onlineSessionMap = WebSocketManagerCache.onlineSessionMap;
        for (Map.Entry<String, WsSession> sessionEntry : onlineSessionMap.entrySet()) {
            String sid = sessionEntry.getKey();
            WsSession wsSession = sessionEntry.getValue();
            // 清理 redis
            wsRedisStatisticsAndManage.removeUserBySessionId(sid);
            // 断开websocket 连接 ...
            WebSocketSession session = wsSession.getSession();
            if (session != null) {
                try {
                    wsSession.getSession().close(CloseStatus.SERVICE_RESTARTED);
                    log.info(" 优雅退出，关闭 websocket 连接 ...");
                } catch (IOException e) {
                    log.error("【websocket消息】连接断开异常，error: {}", e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }

    }

}
