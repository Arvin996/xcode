package cn.xk.xcode.core.monitor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xuk
 * @Date 2024/11/14 10:23
 * @Version 1.0.0
 * @Description MonitorClientInstanceCounter
 **/
public class MonitorClientInstanceCounter {

    private static final ConcurrentHashMap<String, Integer> INSTANCE_COUNTER = new ConcurrentHashMap<>();

    public static Integer put(String k) {
        if (containsKey(k)) {
            INSTANCE_COUNTER.put(k, INSTANCE_COUNTER.get(k) + 1);
        } else {
            INSTANCE_COUNTER.put(k, 1);
        }
        return INSTANCE_COUNTER.get(k);
    }

    public static void remove(String k) {
        if (containsKey(k)) {
            if (INSTANCE_COUNTER.get(k) > 1) {
                INSTANCE_COUNTER.put(k, INSTANCE_COUNTER.get(k) - 1);
            } else {
                INSTANCE_COUNTER.remove(k);
            }
        }
    }

    public static Integer get(String k){
        return INSTANCE_COUNTER.get(k);
    }

    private static boolean containsKey(String k) {
        return INSTANCE_COUNTER.containsKey(k);
    }
}
