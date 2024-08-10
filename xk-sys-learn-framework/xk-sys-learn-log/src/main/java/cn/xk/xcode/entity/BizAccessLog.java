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
    /**
     * 链路id
     */
    private String traceId;
    /**
     * 业务id
     */
    private String bizId;
    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 登录类型
     */
    private String LoginType;
    /**
     * 访问地址
     */
    private String url;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 访问方法
     */
    private String method;
    /**
     * 访问ip
     */
    private String ip;
    /**
     * 参数类型
     */
    private String contentType;
    /**
     * 方法参数
     */
    private String methodParams;
    /**
     * 方法名称
     */
    private String methodName;
    /**
     * 类名
     */
    private String className;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 总时长
     */
    private Long duration;
    /**
     * 接口描述
     */
    private String desc;
    /**
     * /ip所在城市 例如: 北京市等等
     */
    private String ipAddress;
    /**
     * 返回值
     */
    private String result;
}
