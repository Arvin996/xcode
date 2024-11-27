package cn.xk.xcode.core.subject;

import cn.xk.xcode.core.observer.Observer;
import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;
import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/27 14:06
 * @Version 1.0.0
 * @Description NacosServerChangeSubject
 **/
@RequiredArgsConstructor
public class NacosServerChangeSubject implements Subject {

    private final List<Observer> observers;

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(InstancesChangeEvent instancesChangeEvent) {
        for (Observer observer : observers) {
            observer.updateServiceInstance(instancesChangeEvent);
        }
    }
}
