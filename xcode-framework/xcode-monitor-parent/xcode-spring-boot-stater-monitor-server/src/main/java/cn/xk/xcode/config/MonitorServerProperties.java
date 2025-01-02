package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author xuk
 * @Date 2024/11/14 10:09
 * @Version 1.0.0
 * @Description MonitorServerProperties
 **/
@SuppressWarnings("all")
@ConfigurationProperties("xcode.monitor.server")
@Data
public class MonitorServerProperties {

    // 心跳检测的时间间隔
    private Long heartbeatInterval = 60L;

    // 心跳检测的超时时间
    private Integer heartBeatTimeout = 1;
}
