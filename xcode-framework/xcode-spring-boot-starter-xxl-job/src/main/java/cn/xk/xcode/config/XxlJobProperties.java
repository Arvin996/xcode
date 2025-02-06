package cn.xk.xcode.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/7/2 19:28
 * @description
 */
@ConfigurationProperties("xcode.xxl.job")
@Data
public class XxlJobProperties {

    private boolean enable;

    private String username;

    private String password;

    private String executorTitle;

    private String adminAddress;

    private String accessToken;

    private String appname;

    private String address;

    private String ip;

    private int port;

    private String logPath;

    private int logRetentionDays;
}
