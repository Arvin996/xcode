package cn.xk.xcode.core;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
public class WsSession {
    private String sid;
    private String loginId;
    private WebSocketSession session;

    public WsSession(String sid, String loginId, WebSocketSession session) {
        this.sid = sid;
        this.loginId = loginId;
        this.session = session;
    }
}