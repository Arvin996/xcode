package cn.xk.xcode.factory;

import cn.xk.xcode.client.TakeoutUserClient;
import cn.xk.xcode.entity.vo.TakeoutUserResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collection;
import java.util.List;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/10/29 15:37
 * @Version 1.0.0
 * @Description TakeoutUserClientFallbackFactory
 **/
public class TakeoutUserClientFallbackFactory implements FallbackFactory<TakeoutUserClient> {
    @Override
    public TakeoutUserClient create(Throwable cause) {
        return new TakeoutUserClient() {
            @Override
            public CommonResult<TakeoutUserResultVo> getTakeoutUser(Long userId) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<List<TakeoutUserResultVo>> getTakeUserList(Collection<Long> ids) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<List<TakeoutUserResultVo>> getUserListByName(String name) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<TakeoutUserResultVo> getUserByMobile(String mobile) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
