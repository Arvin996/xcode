package cn.xk.xcode.enums;

import cn.xk.xcode.core.annotation.StringEnumValueToArray;
import cn.xk.xcode.utils.collections.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2025/3/10 16:35
 * @Version 1.0.0
 * @Description ReceiverType
 **/
@AllArgsConstructor
@Getter
public enum ReceiverTypeEnum implements StringEnumValueToArray {

    PLAIN("00", "plain文本描述 逗号隔开"),
    CSV("10", "csv文件导入");

    private final String code;
    private final String desc;

    @Override
    public String[] toArrayString() {
        return ArrayUtil.toArray(ReceiverTypeEnum.values(), ReceiverTypeEnum::getCode);
    }

    public static boolean isCsv(String code){
        return CSV.getCode().equals(code);
    }
}
