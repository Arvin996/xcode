package cn.xk.xcode.core.monitor;

import java.util.concurrent.ConcurrentHashMap;


/**
 * @Author xuk
 * @Date 2024/11/14 10:27
 * @Version 1.0.0
 * @Description MonitorClientInstanceContext
 **/
public class MonitorClientInstanceContext {

    private static final ConcurrentHashMap<String, MonitorClientInstance> INSTANCE_MAP = new ConcurrentHashMap<>();

    public static void put(MonitorClientInstance instance) {
        String applicationName = instance.getApplicationName();
        Integer count = MonitorClientInstanceCounter.put(applicationName);
        instance.setCount(count);
        INSTANCE_MAP.put(applicationName + "-" + instance.getIp(), instance);
    }

    public static void update(MonitorClientInstance instance){
        INSTANCE_MAP.put(instance.getApplicationName() + "-" + instance.getIp(), instance);
    }

    public static MonitorClientInstance get(String name, String ip) {
        return INSTANCE_MAP.getOrDefault(name + "-" + ip, null);
    }

    public static void remove(MonitorClientInstance instance) {
        INSTANCE_MAP.remove(instance.getApplicationName() + "-" + instance.getIp());
        MonitorClientInstanceCounter.remove(instance.getApplicationName());
    }

    public static ConcurrentHashMap<String, MonitorClientInstance> getInstanceMap() {
        return INSTANCE_MAP;
    }
}
