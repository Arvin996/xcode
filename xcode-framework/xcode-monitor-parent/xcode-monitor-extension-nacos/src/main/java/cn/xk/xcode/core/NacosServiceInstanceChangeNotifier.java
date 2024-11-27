package cn.xk.xcode.core;

import cn.xk.xcode.core.subject.Subject;
import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;
import com.alibaba.nacos.common.notify.Event;
import com.alibaba.nacos.common.notify.NotifyCenter;
import com.alibaba.nacos.common.notify.listener.Subscriber;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/11/27 14:17
 * @Version 1.0.0
 * @Description NacosServiceInstanceChangeNotifier
 **/
@Slf4j
public class NacosServiceInstanceChangeNotifier extends Subscriber<InstancesChangeEvent> {

    @Resource
    private Subject nacosServerChangeSubject;

    @PostConstruct
    public void registerToNotifyCenter() {
        NotifyCenter.registerSubscriber(this);
    }

    @Override
    public void onEvent(InstancesChangeEvent instancesChangeEvent) {
        // 服务上线a{"clusters":"","groupName":"DEFAULT_GROUP","hosts"
        // :[{"clusterName":"DEFAULT","enabled":true,"ephemeral":true,"healthy":true
        // ,"instanceHeartBeatInterval":5000,"instanceHeartBeatTimeOut":15000
        // ,"ip":"192.168.198.1","ipDeleteTimeout":30000,"metadata"
        // :{"preserved.register.source":"SPRING_CLOUD"},"port":8077,"serviceName"
        // :"DEFAULT_GROUP@@userservice","weight":1.0},{"clusterName":"DEFAULT"
        // ,"enabled":true,"ephemeral":true,"healthy":true,"instanceHeartBeatInterval":5000
        // ,"instanceHeartBeatTimeOut":15000,"ip":"192.168.198.1","ipDeleteTimeout":30000
        // ,"metadata":{"preserved.register.source":"SPRING_CLOUD"},"port":8079
        // ,"serviceName":"DEFAULT_GROUP@@userservice","weight":1.0}],"pluginEvent"
        // :false,"serviceName":"userservice"}
        //服务下线
        //{"clusters":"","groupName":"DEFAULT_GROUP","hosts"
        // :[{"clusterName":"DEFAULT","enabled":true,"ephemeral":true
        // ,"healthy":true,"instanceHeartBeatInterval":5000
        // ,"instanceHeartBeatTimeOut":15000,"ip":"192.168.198.1"
        // ,"ipDeleteTimeout":30000,"metadata":{"preserved.register.source":
        // "SPRING_CLOUD"},"port":8079,"serviceName":"DEFAULT_GROUP@@userservice","weight":1.0}]
        // ,"pluginEvent":false,"serviceName":"userservice"}
        nacosServerChangeSubject.notifyObservers(instancesChangeEvent);
    }

    @Override
    public Class<? extends Event> subscribeType() {
        return InstancesChangeEvent.class;
    }
}
