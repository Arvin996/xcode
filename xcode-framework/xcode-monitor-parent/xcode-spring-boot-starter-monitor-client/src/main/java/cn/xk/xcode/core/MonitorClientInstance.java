package cn.xk.xcode.core;

import cn.hutool.core.date.LocalDateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2024/11/14 10:17
 * @Version 1.0.0
 * @Description MonitorClientInstance
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MonitorClientInstance implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private String applicationName;
    private String port;
    private String ipv4;
    private String ipv6;
    private String ip;
    private String ipType;
    private String webContextPath = "";
    private String actuatorPath;
    private Map<String, Object> metaData;
    private Integer count;
    private String status = "UP"; // "UP", "DOWN"
    private String lastUpdateTime = LocalDateTimeUtil.formatNormal(LocalDateTime.now());
}
