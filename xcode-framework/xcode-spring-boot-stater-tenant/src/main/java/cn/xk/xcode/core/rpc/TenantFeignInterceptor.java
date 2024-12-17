package cn.xk.xcode.core.rpc;

import cn.xk.xcode.core.context.TenantContext;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/12/17 15:47
 * @Version 1.0.0
 * @Description TenantFeignInterceptor
 **/
public class TenantFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long tenantId = TenantContext.getTenantId();
        if (Objects.nonNull(tenantId)) {
            requestTemplate.header(TenantContext.TENANT_NAME, tenantId.toString());
        }
    }
}
