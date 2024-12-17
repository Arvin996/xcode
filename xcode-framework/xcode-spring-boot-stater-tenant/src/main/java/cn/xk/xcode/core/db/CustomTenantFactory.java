package cn.xk.xcode.core.db;

import cn.xk.xcode.config.TenantProperties;
import cn.xk.xcode.core.context.TenantContext;
import com.mybatisflex.core.tenant.TenantFactory;
import lombok.RequiredArgsConstructor;

/**
 * @Author xuk
 * @Date 2024/12/17 15:37
 * @Version 1.0.0
 * @Description XcodeCustomTenantFactory
 **/
@RequiredArgsConstructor
public class CustomTenantFactory implements TenantFactory {

    private final TenantProperties tenantProperties;

    @Override
    public Object[] getTenantIds() {
        if (!tenantProperties.isEnabled()){
            return new Object[]{};
        }
        return new Object[]{TenantContext.getTenantId()};
    }

    @Override
    public Object[] getTenantIds(String tableName) {
        if (tenantProperties.getIgnoreTables().contains(tableName)){
            return new Object[]{};
        }
        return getTenantIds();
    }
}
