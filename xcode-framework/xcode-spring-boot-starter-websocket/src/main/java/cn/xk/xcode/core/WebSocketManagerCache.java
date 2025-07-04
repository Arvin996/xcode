package cn.xk.xcode.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xuk
 * @Date 2024/7/2 14:39
 * @Version 1.0
 * @Description WebSocketManagerCache
 */
public class WebSocketManagerCache {
    /**
     * sid <==> session 关系
     */
    public static ConcurrentHashMap<String, WsSession> onlineSessionMap = new ConcurrentHashMap<>();

    /**
     * loginId <==> sid 关系; 1对多,一个用户有可能在多个浏览器上登录
     */
    public static ConcurrentHashMap<String, List<String>> onlineUserSessionIdMap = new ConcurrentHashMap<>();

    public static void addOnlineSid(String loginId, String sid) {
        List<String> sidArray;
        if (onlineUserSessionIdMap.containsKey(loginId)) {
            sidArray = onlineUserSessionIdMap.get(loginId);
        } else {
            sidArray = new ArrayList<>();
        }
        sidArray.add(sid);
        onlineUserSessionIdMap.put(loginId, sidArray);
    }

    /**
     * 清除断线的sid
     */
    public static void removeUserSession(String sid) {
        WsSession wsSession = onlineSessionMap.get(sid);
        String loginId = wsSession.getLoginId();
        if (onlineUserSessionIdMap.containsKey(loginId)) {
            List<String> sids = onlineUserSessionIdMap.get(loginId);
            sids.remove(sid);
            onlineUserSessionIdMap.put(loginId, sids);
        }
    }
}
