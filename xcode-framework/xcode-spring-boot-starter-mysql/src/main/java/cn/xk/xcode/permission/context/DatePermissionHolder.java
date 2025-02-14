package cn.xk.xcode.permission.context;

import cn.xk.xcode.enums.DataPermissionScope;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

/**
 * @Author xuk
 * @Date 2024/12/19 13:53
 * @Version 1.0.0
 * @Description DatePermissionHolder
 **/
public class DatePermissionHolder {

    // 这是为了解决多个@DataPermission的情况 防止覆盖
    private static final ThreadLocal<LinkedList<DataScopeEntity>> DATE_SCOPE_HOLDER = TransmittableThreadLocal.withInitial(LinkedList::new);

    public static void add(DataPermissionScope scope, Class<?> tableClazz) {
        DATE_SCOPE_HOLDER.get().addLast(new DataScopeEntity(scope, tableClazz));
    }

    public static DataScopeEntity get(){
        return DATE_SCOPE_HOLDER.get().peekLast();
    }

    public static void remove(){
        LinkedList<DataScopeEntity> linkedList = DATE_SCOPE_HOLDER.get();
        if (linkedList.isEmpty()) {
            DATE_SCOPE_HOLDER.remove();
            return;
        }
        linkedList.removeLast();
        if (linkedList.isEmpty()) {
            DATE_SCOPE_HOLDER.remove();
        }
    }


    @Data
    @AllArgsConstructor
    public static class DataScopeEntity {
        // 数据权限类型
        private DataPermissionScope scope;
        Class<?> tableClazz;
    }

}
