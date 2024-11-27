package cn.xk.xcode.core.context;

import cn.xk.xcode.core.tuple.TwoTuple;
import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author xuk
 * @Date 2024/11/27 14:42
 * @Version 1.0.0
 * @Description ServiceInstanceCounter
 **/
public class ServiceInstanceCounter {

    // key就是serviceName服务名 第二个是二元元组 元组第一个值是监控实例个数 第二个是非健康实例格式
    private static final ConcurrentHashMap<String, TwoTuple<Integer, Integer>> SERVICE_INSTANCE_COUNTER = new ConcurrentHashMap<>();

    // 服务实例挂了怎么做
    public static void up(Instance instance) {
    }

    // 服务实例恢复了怎么做 或者说是第一次添加
    public static void down(Instance instance) {
    }

    public Integer getHealthCount(String serviceName){
        TwoTuple<Integer, Integer> twoTuple = SERVICE_INSTANCE_COUNTER.getOrDefault(serviceName, null);
        if (Objects.isNull(twoTuple)){
            return 0;
        }
        return twoTuple.getFirstValue();
    }

    public Integer getUnHealthCount(String serviceName){
        TwoTuple<Integer, Integer> twoTuple = SERVICE_INSTANCE_COUNTER.getOrDefault(serviceName, null);
        if (Objects.isNull(twoTuple)){
            return 0;
        }
        return twoTuple.getSecondValue();
    }

    public ConcurrentHashMap<String, TwoTuple<Integer, Integer>>  getServiceInstanceCounter(){
        return SERVICE_INSTANCE_COUNTER;
    }

}
