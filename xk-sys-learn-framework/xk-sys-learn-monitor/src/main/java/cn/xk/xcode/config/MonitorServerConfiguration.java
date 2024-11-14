package cn.xk.xcode.config;

import cn.xk.xcode.core.monitor.controller.MonitorServerController;
import cn.xk.xcode.core.task.MonitorHeartbeatTask;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/11/14 14:29
 * @Version 1.0.0
 * @Description MonitorServerConfiguration
 **/
public class MonitorServerConfiguration {

    @Resource
    private MonitorServerProperties monitorServerProperties;

    @Bean
    public MonitorHeartbeatTask monitorHeartbeatTask() {
        return new MonitorHeartbeatTask(monitorServerProperties);
    }

    @Bean
    public MonitorServerController monitorServerController() {
        return new MonitorServerController();
    }
}
