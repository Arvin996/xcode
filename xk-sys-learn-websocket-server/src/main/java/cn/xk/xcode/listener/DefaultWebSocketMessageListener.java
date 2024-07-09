package cn.xk.xcode.listener;

import cn.hutool.json.JSONUtil;
import cn.xk.xcode.message.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Author xuk
 * @Date 2024/7/9 13:31
 * @Version 1.0
 * @Description DefaultWebSocketMessageListener
 */
@Slf4j
@Component
public class DefaultWebSocketMessageListener implements WebSocketMessageListener{

    @Override
    public void onMessage(WebSocketSession session, MessageEntity message) {
        // todo 处理消息
        log.info("收到消息{}", JSONUtil.toJsonStr(message));
    }

    @Override
    public String getMsgType() {
        return "default";
    }
}
