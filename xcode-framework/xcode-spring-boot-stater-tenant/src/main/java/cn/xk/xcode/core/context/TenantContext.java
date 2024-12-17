package cn.xk.xcode.core.context;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @Author xuk
 * @Date 2024/12/17 15:16
 * @Version 1.0.0
 * @Description TenantContext
 **/
public class TenantContext {

    public static final String TENANT_NAME = "tenantId";
    private static final ErrorCode TENANT_ID_NOT_EXIST = new IntErrorCode(1400_0_500, "租户id不存在");

    /**
     * 当前租户编号
     */
    private static final ThreadLocal<Long> TENANT_ID = new TransmittableThreadLocal<>();

    /**
     * 是否忽略租户
     */
    public static final ThreadLocal<Boolean> IGNORE_TENANT = new TransmittableThreadLocal<>();

    public static Long getTenantId() {
        return TENANT_ID.get();
    }

    public static Long getRequiredTenantId() {
        Long tenantId = getTenantId();
        return ObjectUtil.returnIfNotNullCastServiceEx(tenantId, TENANT_ID_NOT_EXIST);
    }

    public static void setTenantId(Long tenantId) {
        TENANT_ID.set(tenantId);
    }

    public static void setIgnore(Boolean ignore) {
        IGNORE_TENANT.set(ignore);
    }

    public static boolean isIgnore() {
        return Boolean.TRUE.equals(IGNORE_TENANT.get());
    }

    public static void clear() {
        TENANT_ID.remove();
        IGNORE_TENANT.remove();
    }
}
