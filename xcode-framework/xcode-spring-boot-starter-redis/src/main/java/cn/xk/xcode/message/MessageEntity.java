package cn.xk.xcode.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/7/1 16:16
 * @Version 1.0
 * @Description Message
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {
    // 携带渠道信息及消息类型
    private Header header;

    private List<String> toUsers = new ArrayList<>();

    // 发送消息体
    private Object data;

    private String fromUser;
}
