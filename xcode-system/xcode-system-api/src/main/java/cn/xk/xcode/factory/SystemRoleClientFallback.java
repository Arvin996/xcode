package cn.xk.xcode.factory;

import cn.xk.xcode.client.SystemRoleClient;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/11/20 16:11
 * @Version 1.0.0
 * @Description SystemRoleClientFallback
 **/
public class SystemRoleClientFallback implements FallbackFactory<SystemRoleClient> {
    @Override
    public SystemRoleClient create(Throwable cause) {
        return id -> CommonResult.error(SERVICE_FALL_BACK);
    }
}
