package cn.xk.xcode.enums;

import cn.xk.xcode.core.annotation.StringEnumValueToArray;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2025/3/10 16:02
 * @Version 1.0.0
 * @Description MessageSendType
 **/
@Getter
@AllArgsConstructor
public enum MessageSendType implements StringEnumValueToArray {

    CORN("corn", "定时发送"),
    DELAY("delay", "延时发送"),
    NOW("now", "立即发送");
    private final String code;
    private final String desc;

    @Override
    public String[] toArrayString() {
        return new String[]{CORN.getCode(), DELAY.getCode(), NOW.getCode()};
    }
}
