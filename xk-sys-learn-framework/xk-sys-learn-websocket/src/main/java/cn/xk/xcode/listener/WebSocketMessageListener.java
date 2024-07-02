package cn.xk.xcode.listener;

import cn.xk.xcode.message.MessageEntity;
import org.springframework.web.socket.WebSocketSession;

/**
 * @Author xuk
 * @Date 2024/7/2 13:09
 * @Version 1.0
 * @Description WebSocketMessageListener
 */
public interface WebSocketMessageListener
{
    /**
     * 处理消息
     *
     * @param session Session
     * @param message 消息
     */
    void onMessage(WebSocketSession session, MessageEntity message);

    /**
     * 获得消息类型
     * @return 消息类型
     */
    String getMsgType();
}
