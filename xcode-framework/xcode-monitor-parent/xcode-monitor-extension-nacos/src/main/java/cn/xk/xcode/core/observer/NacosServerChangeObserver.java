package cn.xk.xcode.core.observer;

import cn.xk.xcode.core.context.ServiceInstanceContext;
import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;

/**
 * @Author xuk
 * @Date 2024/11/27 14:05
 * @Version 1.0.0
 * @Description NacosServerChangeObserver
 **/
public class NacosServerChangeObserver implements Observer {

    @Override
    public void updateServiceInstance(InstancesChangeEvent instancesChangeEvent) {
        ServiceInstanceContext.updateServerInstance(instancesChangeEvent);
    }
}
