package cn.xk.xcode.core.subject;

import cn.xk.xcode.core.observer.Observer;
import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;

/**
 * @Author xuk
 * @Date 2024/11/27 14:06
 * @Version 1.0.0
 * @Description Subject 主题
 **/
public interface Subject {

    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(InstancesChangeEvent instancesChangeEvent);
}
