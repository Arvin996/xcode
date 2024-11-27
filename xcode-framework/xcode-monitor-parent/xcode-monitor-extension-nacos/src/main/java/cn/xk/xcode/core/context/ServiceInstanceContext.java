package cn.xk.xcode.core.context;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xuk
 * @Date 2024/11/27 14:09
 * @Version 1.0.0
 * @Description ServerInstanceContext
 **/
public class ServiceInstanceContext {

    // 内存存储服务实例 key为服务的集群名+服务名+ip+端口
    private static final ConcurrentHashMap<String, Instance> SERVER_INSTANCE_CONTEXT = new ConcurrentHashMap<>();

    public static void registerServerInstance(InstancesChangeEvent instancesChangeEvent) {
    }

    // 有服务就更新 没有就进行注册
    public static void updateServerInstance(InstancesChangeEvent instancesChangeEvent) {
    }

    public static void removeServerInstance(InstancesChangeEvent instancesChangeEvent) {
    }

    public static ConcurrentHashMap<String, Instance> getContext() {
        return SERVER_INSTANCE_CONTEXT;
    }
}
