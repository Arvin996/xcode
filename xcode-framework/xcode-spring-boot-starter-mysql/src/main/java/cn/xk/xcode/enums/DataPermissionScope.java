package cn.xk.xcode.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author xuk
 * @Date 2024/12/19 13:29
 * @Version 1.0.0
 * @Description DataPermissionScope
 **/
@Getter
@AllArgsConstructor
public enum DataPermissionScope {

    SELF("本人创建的数据"),
    DEPT("本部门的数据"),
    DEPT_CHILD("本部门及下属部门的数据"),
    ALL("所有数据");
    private final String desc;

}
