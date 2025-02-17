package cn.xk.xcode.config;

import cn.xk.xcode.core.MonitorClientStartLoader;
import cn.xk.xcode.core.task.MonitorClientServiceSendTask;
import org.springframework.context.annotation.Bean;

/**
 * @Author xuk
 * @Date 2024/11/14 8:58
 * @Version 1.0.0
 * @Description MonitorClientConfiguration
 **/
public class MonitorClientConfiguration {

    @Bean
    public MonitorClientProperties monitorClientProperties(){
        return new MonitorClientProperties();
    }

    @Bean
    public MonitorClientStartLoader monitorClientStartLoader(MonitorClientProperties monitorClientProperties){
        return new MonitorClientStartLoader(monitorClientProperties);
    }

    @Bean
    public MonitorClientServiceSendTask monitorClientServiceSendTask(MonitorClientProperties monitorClientProperties){
        return new MonitorClientServiceSendTask(monitorClientProperties);
    }
}
