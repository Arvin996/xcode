package cn.xk.xcode.handler.message;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xukai
 * @version 1.0
 * @date 2025/5/18 16:49
 * @description SingeSendMessageResult
 */
@Data
public class SingeSendMessageResult {
    private boolean success;
    private String failMsg;
    private String receiver;
    private LocalDateTime execTime;
    private LocalDateTime successTime;
}
