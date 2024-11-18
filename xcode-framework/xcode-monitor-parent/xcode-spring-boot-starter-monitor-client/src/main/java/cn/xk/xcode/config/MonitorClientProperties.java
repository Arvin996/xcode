package cn.xk.xcode.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author xuk
 * @Date 2024/11/14 9:08
 * @Version 1.0.0
 * @Description MonitorProperties
 **/
@SuppressWarnings("all")
@ConfigurationProperties("xk.sys.monitor.client")
@Data
public class MonitorClientProperties {

    // 监控服务端地址
    private String monitorServerAddr;
    // 监控服务端contextPath
    private String monitorServerContextPath;
    // 多长时间间隔发送一次 单位秒
    private Long interval = 10L;
    // 监控客户端ip地址
    private String ip;
    // 监控客户端ip类型
    private IpType ipType = null;
    // 网络接口名称
    private String networkInterface = "";


    @AllArgsConstructor
    @Getter
    public enum IpType {

        IP_V4("ipv4"),
        IP_V6("ipv6");
        private final String type;
    }
}
