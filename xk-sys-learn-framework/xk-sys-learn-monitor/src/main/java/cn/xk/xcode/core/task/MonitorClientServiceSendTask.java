package cn.xk.xcode.core.task;

import cn.xk.xcode.config.MonitorClientProperties;
import cn.xk.xcode.core.monitor.MonitorClientStartLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

import static cn.xk.xcode.core.monitor.MonitorClientStartLoader.SERVICE_UPDATE_PATH;

/**
 * @Author xuk
 * @Date 2024/11/14 9:14
 * @Version 1.0.0
 * @Description MonitorClientServiceSendTask
 **/
@RequiredArgsConstructor
public class MonitorClientServiceSendTask implements EnvironmentAware {

    private final MonitorClientProperties monitorClientProperties;
    private Environment environment;

    @Scheduled(fixedDelayString = "#{monitorClientProperties.interval}", timeUnit = TimeUnit.SECONDS)
    public void sendMetrics() {
        MonitorClientStartLoader.registerService(monitorClientProperties, environment, SERVICE_UPDATE_PATH);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
