package cn.xk.xcode.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/7/2 19:54
 * @description
 */
@Data
public class BizAccessLog {
    private String traceId;
    private String userId;
    private String url;
    private LocalDateTime startTime;
    private String method;
    private Object param;
    private String requestBody;
    private String ip;
    private String contentType;
    private String methodParams;
    private String methodName;
    private String className;
    private LocalDateTime endTime;
    private Long duration;
    private String desc; //接口描述
    private String ipAddress; //ip所在城市 例如: 北京市等等
}
