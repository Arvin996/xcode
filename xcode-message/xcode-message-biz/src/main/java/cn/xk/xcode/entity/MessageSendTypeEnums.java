package cn.xk.xcode.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author xuk
 * @Date 2025/3/6 8:29
 * @Version 1.0.0
 * @Description MessageSendTypeEnums
 **/
@AllArgsConstructor
@Getter
public enum MessageSendTypeEnums {

    NOW("now"),
    DELAY("delay"),
    CORN("corn");
    private final String type;

    public static MessageSendTypeEnums getType(String type) {
        return Arrays.stream(MessageSendTypeEnums.values()).filter(messageSendTypeEnums -> messageSendTypeEnums.getType().equals(type)).findFirst().orElse(null);
    }
}
