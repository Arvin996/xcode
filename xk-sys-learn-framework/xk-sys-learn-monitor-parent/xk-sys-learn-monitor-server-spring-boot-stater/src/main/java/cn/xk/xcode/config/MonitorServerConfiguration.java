package cn.xk.xcode.config;


import cn.xk.xcode.core.controller.MonitorServerController;
import cn.xk.xcode.core.task.MonitorHeartbeatTask;
import org.springframework.context.annotation.Bean;

/**
 * @Author xuk
 * @Date 2024/11/14 14:29
 * @Version 1.0.0
 * @Description MonitorServerConfiguration
 **/
public class MonitorServerConfiguration {

    @Bean
    public MonitorServerProperties monitorServerProperties() {
        return new MonitorServerProperties();
    }

//    @Resource
//    private MonitorServerProperties monitorServerProperties;

    @Bean
    public MonitorHeartbeatTask monitorHeartbeatTask(MonitorServerProperties monitorServerProperties) {
        return new MonitorHeartbeatTask(monitorServerProperties);
    }

    @Bean
    public MonitorServerController monitorServerController() {
        return new MonitorServerController();
    }
}
