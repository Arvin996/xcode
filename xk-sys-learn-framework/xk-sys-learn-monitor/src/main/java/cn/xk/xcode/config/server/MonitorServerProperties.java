package cn.xk.xcode.config.server;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author xuk
 * @Date 2024/11/14 10:09
 * @Version 1.0.0
 * @Description MonitorServerProperties
 **/
@Component(value = "monitorServerProperties")
@ConfigurationProperties("xk.sys.monitor.server")
@Data
public class MonitorServerProperties {

    // 心跳检测的时间间隔
    private Long heartbeatInterval = 60L;

    // 心跳检测的超时时间
    private Integer heartBeatTimeout = 1;
}
