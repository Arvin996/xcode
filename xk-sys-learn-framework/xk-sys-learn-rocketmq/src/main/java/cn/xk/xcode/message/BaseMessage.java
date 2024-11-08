package cn.xk.xcode.message;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2024/7/3 09:11
 * @Version 1.0
 * @Description BaseMessage
 */
@Data
public abstract class BaseMessage {
    // 消息主键 例如订单id等
    protected String bizKey = "default";

    // 消息来源
    protected String messageSource;

    // 发送时间
    protected LocalDateTime sendTime;

    // 重试次数 用于判断重试次数，超过重试次数发送异常警告 没有超过则会发送延时消息重复消费
    protected Integer retryTimes;
}
