package cn.xk.xcode.permission.annotation;

import cn.xk.xcode.enums.DataPermissionScope;

import java.lang.annotation.*;

/**
 * @Author xuk
 * @Date 2024/12/19 13:36
 * @Version 1.0.0
 * @Description DataPermission
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataPermission {

    // 数据权限类型
    DataPermissionScope scope() default DataPermissionScope.ALL;

    Class<?> tableClazz();
}
