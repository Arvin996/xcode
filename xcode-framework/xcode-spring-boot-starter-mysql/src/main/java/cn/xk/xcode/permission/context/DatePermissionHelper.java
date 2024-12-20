package cn.xk.xcode.permission.context;

import cn.xk.xcode.enums.DataPermissionScope;

/**
 * @Author xuk
 * @Date 2024/12/19 14:48
 * @Version 1.0.0
 * @Description DatePermissionHelper
 **/
public class DatePermissionHelper {

    public static void start(DataPermissionScope scope, Class<?> tableClazz){
        DatePermissionHolder.add(scope, tableClazz);
    }

    public static void end(){
        DatePermissionHolder.remove();
    }
}
