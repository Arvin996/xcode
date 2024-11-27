package cn.xk.xcode.config;

import cn.xk.xcode.core.NacosServiceInstanceChangeNotifier;
import cn.xk.xcode.core.observer.NacosServerChangeObserver;
import cn.xk.xcode.core.observer.Observer;
import cn.xk.xcode.core.subject.NacosServerChangeSubject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/27 15:34
 * @Version 1.0.0
 * @Description NacosNotifyConfig
 **/
@Configuration
public class NacosNotifyConfig {

    @Bean
    public NacosServiceInstanceChangeNotifier nacosServiceInstanceChangeNotifier() {
        return new NacosServiceInstanceChangeNotifier();
    }

    @Bean
    public NacosServerChangeObserver nacosServerChangeObserver() {
        return new NacosServerChangeObserver();
    }

    @Bean
    public NacosServerChangeSubject nacosServerChangeSubject(List<Observer> observers) {
        return new NacosServerChangeSubject(observers);
    }
}
