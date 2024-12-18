package cn.xk.xcode.core.entity;

import cn.xk.xcode.message.BaseMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2024/12/18 9:52
 * @Version 1.0.0
 * @Description TraceRecord
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TraceRecord extends BaseMessage {

    // 链路id
    private String traceId;
    // 调用顺序
    private Integer traceSort;
    // 请求时间
    private String requestTime;
    // 请求参数
    private String requestParam;
    // 调用服务
    private String callServiceName;
    // 被调服务
    private String beCalledServiceName;
    // 调用方法
    private String callMethod;
    // 响应结果
    private String responseResult;
    // 请求耗时
    private Long requestTimeCost;
    // 返回时间
    private String responseTime;
    // 请求结果0 成功 1 业务异常 2系统异常
    private Integer requestResultCode;
    // 异常信息
    private String exceptionMessage;
}
