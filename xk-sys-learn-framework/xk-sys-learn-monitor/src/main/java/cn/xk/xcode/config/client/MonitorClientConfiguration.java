package cn.xk.xcode.config.client;

import cn.xk.xcode.core.monitor.client.MonitorClientStartLoader;
import cn.xk.xcode.core.task.MonitorClientServiceSendTask;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/11/14 8:58
 * @Version 1.0.0
 * @Description MonitorClientConfiguration
 **/
public class MonitorClientConfiguration {

    @Resource
    private MonitorClientProperties monitorClientProperties;

    @Bean
    public MonitorClientStartLoader monitorClientStartLoader(){
        return new MonitorClientStartLoader(monitorClientProperties);
    }

    @Bean
    public MonitorClientServiceSendTask monitorClientServiceSendTask(){
        return new MonitorClientServiceSendTask(monitorClientProperties);
    }
}
