package cn.xk.xcode.context;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/11/13 14:05
 * @Version 1.0.0
 * @Description RegisterServiceContext
 **/
public class RegisterMonitorServiceContext {

    private static final Map<String, String>  SERVICE_MAP = new HashMap<>();

    public static void put(String key, String value){
        SERVICE_MAP.put(key, value);
    }

    public static String get(String key){
        return SERVICE_MAP.get(key);
    }

    public static void remove(String key){
        SERVICE_MAP.remove(key);
    }

    public static void clear(){
        SERVICE_MAP.clear();
    }

    public static Map<String, String> getInstance(){
        return SERVICE_MAP;
    }

    public static boolean isContains(String key){
        return SERVICE_MAP.containsKey(key);
    }
}

