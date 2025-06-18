package cn.xk.xcode.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2025/6/16 10:34
 * @Version 1.0.0
 * @Description LoginStpType
 **/
@AllArgsConstructor
@Getter
public enum LoginStpType {

    SYSTEM("system"),
    MEMBER("member"),
    NONE("none");
    private final String type;

    public static LoginStpType getType(String type) {
        for (LoginStpType stpType : LoginStpType.values()) {
            if (stpType.type.equals(type)) {
                return stpType;
            }
        }
        return null;
    }
}
