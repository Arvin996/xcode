package cn.xk.xcode.event;

import cn.xk.xcode.core.WsState;
import cn.xk.xcode.handler.WebSocketMessageHandler;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/7/2 14:49
 * @Version 1.0
 * @Description StateChangeListener
 */
@Component
public class StateChangeListener implements ApplicationListener<StateChangeEvent> {

    @Resource
    private WsState wsState;

    @Resource
    private WebSocketMessageHandler webSocketMessageHandler;

    @Override
    public void onApplicationEvent(StateChangeEvent event) {
        boolean state = wsState.isState();
        wsState.setState(!state);
        if (!state) {
            webSocketMessageHandler.disconnectAll();
        }
    }
}
