package cn.xk.xcode.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/7/1 16:05
 * @Version 1.0
 * @Description header
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Header {
    // 消息类型
    private String type;

    // 消息发送渠道
    private String channel = "default";
}
