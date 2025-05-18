package cn.xk.xcode.core;

import cn.xk.xcode.handler.WebSocketMessageHandler;
import cn.xk.xcode.listener.AbstractRedisMessageListener;
import cn.xk.xcode.message.MessageEntity;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/7/2 15:32
 * @Version 1.0
 * @Description DefaultRedisMessageListener
 */
@Component
public class DefaultRedisMessageListener extends AbstractRedisMessageListener {

    @Resource
    private WebSocketMessageHandler webSocketMessageHandler;

    @Override
    public String channel() {
        return "default";
    }

    @Override
    public void onMessage(MessageEntity messageEntity) {
        webSocketMessageHandler.sendMessage(messageEntity);
    }
}
