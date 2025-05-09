package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author xuk
 * @Date 2025/3/5 17:06
 * @Version 1.0.0
 * @Description MessageChannelEnums
 **/
@AllArgsConstructor
@Getter
public enum MessageChannelEnums {

    SMS("sms"),
    EMAIL("email"),
    WX("wx"),
    DINGDING("dingding"),
    GE_TUI("getui"),
    WXLIVE("wxlive");
    private final String channel;

    public static MessageChannelEnums getChannel(String channel) {
        return Arrays.stream(MessageChannelEnums.values()).filter(messageChannelEnums -> messageChannelEnums.getChannel().equals(channel)).findFirst().orElse(null);
    }
}
