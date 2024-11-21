package cn.xk.xcode.factory;

import cn.xk.xcode.client.SystemUserClient;
import cn.xk.xcode.entity.SystemUserResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/11/18 16:07
 * @Version 1.0.0
 * @Description SystemUserClientFallback
 **/
public class SystemUserClientFallback implements FallbackFactory<SystemUserClient> {
    @Override
    public SystemUserClient create(Throwable cause) {
        return new SystemUserClient() {

            @Override
            public CommonResult<SystemUserResultVo> getUser(Long id) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<List<SystemUserResultVo>> selectNotUserList(Collection<Long> userIds) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<List<SystemUserResultVo>> selectUserList(Collection<Long> userIds) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

        };
    }
}
