package cn.xk.xcode.core.observer;

import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;

/**
 * @Author xuk
 * @Date 2024/11/27 14:05
 * @Version 1.0.0
 * @Description Observer
 **/
public interface Observer {

    void updateServiceInstance(InstancesChangeEvent instancesChangeEvent);
}
