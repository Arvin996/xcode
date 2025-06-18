package cn.xk.xcode.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2024/11/22 9:45
 * @Version 1.0.0
 * @Description StpType
 **/
@AllArgsConstructor
@Getter
public enum PermissionStpType {

    SYSTEM("system"),
    MEMBER("member"),
    MIXED("mixed");
    private final String type;

    public static PermissionStpType getType(String type) {
        for (PermissionStpType stpType : PermissionStpType.values()) {
            if (stpType.type.equals(type)) {
                return stpType;
            }
        }
        return null;
    }
}
