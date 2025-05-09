package cn.xk.xcode.enums;

import cn.xk.xcode.core.annotation.StringEnumValueToArray;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

/**
 * 屏蔽类型
 *
 * @author 3y
 */
@Getter
@ToString
@AllArgsConstructor
public enum ShieldType implements StringEnumValueToArray {


    /**
     * 模板设置为夜间不屏蔽
     */
    NIGHT_NO_SHIELD("10", "夜间不屏蔽"),
    /**
     * 模板设置为夜间屏蔽  -- 凌晨接受到的消息会过滤掉
     */
    NIGHT_SHIELD("20", "夜间屏蔽 次日9点发送"),
    /**
     * 时间区间屏蔽， 但是过了这个时间区间会自动发送
     */
    SHIELD_AT_BETWEEN_TIME("30", "某个时间区间屏蔽 如2100-2300");

    private final String code;
    private final String description;

    @Override
    public String[] toArrayString() {
        return Arrays.stream(values()).map(ShieldType::getCode).toArray(String[]::new);
    }
}
