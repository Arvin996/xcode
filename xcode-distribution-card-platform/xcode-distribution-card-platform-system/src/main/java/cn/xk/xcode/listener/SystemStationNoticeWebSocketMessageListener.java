package cn.xk.xcode.listener;

import cn.xk.xcode.message.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Author xuk
 * @Date 2025/5/14 9:28
 * @Version 1.0.0
 * @Description SystemStationNoticeWebSocketMessageListener
 **/
@Component
@Slf4j
public class SystemStationNoticeWebSocketMessageListener implements WebSocketMessageListener {

    @Override
    public void onMessage(WebSocketSession session, MessageEntity message) {
        log.info(message.getData().toString());
    }

    @Override
    public String getMsgType() {
        return "system_station_notice";
    }
}
