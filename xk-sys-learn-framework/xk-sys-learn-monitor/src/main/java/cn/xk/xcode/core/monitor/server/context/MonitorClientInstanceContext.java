package cn.xk.xcode.core.monitor.server.context;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.core.monitor.client.MonitorClientInstance;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Author xuk
 * @Date 2024/11/14 10:27
 * @Version 1.0.0
 * @Description MonitorClientInstanceContext
 **/
public class MonitorClientInstanceContext {

    private static final ConcurrentHashMap<String, Map<String, MonitorClientInstance>> INSTANCE_MAP = new ConcurrentHashMap<>();

    public static void put(MonitorClientInstance instance) {
        String applicationName = instance.getApplicationName();
        Integer count = MonitorClientInstanceCounter.put(applicationName);
        instance.setCount(count);
        if (ObjectUtil.isEmpty(INSTANCE_MAP.get(applicationName))){
            INSTANCE_MAP.put(applicationName, new HashMap<>());
            INSTANCE_MAP.get(applicationName).put(applicationName + "-" + instance.getIp(), instance);
        }else {
            INSTANCE_MAP.get(applicationName).put(applicationName + "-" + instance.getIp(), instance);
        }
    }

    public static void update(MonitorClientInstance instance){
        String applicationName = instance.getApplicationName();
        Map<String, MonitorClientInstance> map = INSTANCE_MAP.get(applicationName);
        if (ObjectUtil.isEmpty(map)){
            put(instance);
            return;
        }
        MonitorClientInstance clientInstance = get(instance.getApplicationName(), instance.getIp());
        if (ObjectUtil.isNull(clientInstance)){
            put(instance);
            return;
        }
        map.put(applicationName + "-" + instance.getIp(), instance);
    }

    public static MonitorClientInstance get(String name, String ip) {
        Map<String, MonitorClientInstance> map = INSTANCE_MAP.get(name);
        if (ObjectUtil.isEmpty(map)){
            return null;
        }
        return map.getOrDefault(name + "-" + ip, null);
    }

    public static void remove(MonitorClientInstance instance) {
        MonitorClientInstanceCounter.remove(instance.getApplicationName());
        Map<String, MonitorClientInstance> map = INSTANCE_MAP.get(instance.getApplicationName());
        if (ObjectUtil.isEmpty(map)){
            return;
        }
        map.remove(instance.getApplicationName() + "-" + instance.getIp());
    }

    public static ConcurrentHashMap<String, Map<String, MonitorClientInstance>> getInstanceMap() {
        return INSTANCE_MAP;
    }

}
