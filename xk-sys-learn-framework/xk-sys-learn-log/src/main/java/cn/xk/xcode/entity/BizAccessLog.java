package cn.xk.xcode.entity;

import cn.xk.xcode.message.BaseMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/7/2 19:54
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BizAccessLog extends BaseMessage {
    private String traceId;
    private String bizId;
    private String bizType;
    private String userId;
    private String LoginType;
    private String url;
    private LocalDateTime startTime;
    private String method;
    private String ip;
    private String contentType;
    private String methodParams;
    private String methodName;
    private String className;
    private LocalDateTime endTime;
    private Long duration;
    private String desc; //接口描述
    private String ipAddress; //ip所在城市 例如: 北京市等等
    private String result;
}
