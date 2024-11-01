package cn.xk.xcode.factory;

import cn.xk.xcode.client.TakeoutSetmealClient;
import cn.xk.xcode.entity.setmeal.TakeoutSetmealResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collection;
import java.util.List;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/11/1 16:01
 * @Version 1.0.0
 * @Description TakeoutSetmealClientFallbackFactory
 **/
public class TakeoutSetmealClientFallbackFactory implements FallbackFactory<TakeoutSetmealClient> {
    @Override
    public TakeoutSetmealClient create(Throwable cause) {
        return new TakeoutSetmealClient() {
            @Override
            public CommonResult<List<TakeoutSetmealResultVo>> getSetmealList() {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<TakeoutSetmealResultVo> getSetmeal(Long id) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<List<TakeoutSetmealResultVo>> getSetmeals(Collection<Long> id) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
