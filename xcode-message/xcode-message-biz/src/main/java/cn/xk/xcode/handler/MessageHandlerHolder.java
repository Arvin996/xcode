package cn.xk.xcode.handler;

import cn.xk.xcode.handler.message.IHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/3/17 16:11
 * @Version 1.0.0
 * @Description MessageHandlerHolder
 **/
public class MessageHandlerHolder {

    private final Map<String, IHandler> handlerMap = new HashMap<>();

    public MessageHandlerHolder (List<IHandler> handlers) {
        handlers.forEach(handler -> handlerMap.put(handler.channelCode(), handler));
    }

    public  IHandler routeHandler(String channelCode) {
        return handlerMap.get(channelCode);
    }
}
