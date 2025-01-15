package cn.xk.xcode.message;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2024/7/3 09:11
 * @Version 1.0
 * @Description BaseMessage
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class BaseMessage {


    // 消息主键 例如订单id等
    protected String bizKey = "default";

    // 消息来源
    protected String messageSource;

    // 发送时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime sendTime;

    // 重试次数 用于判断重试次数，超过重试次数发送异常警告 没有超过则会发送延时消息重复消费
    protected Integer retryTimes = 0;
}
