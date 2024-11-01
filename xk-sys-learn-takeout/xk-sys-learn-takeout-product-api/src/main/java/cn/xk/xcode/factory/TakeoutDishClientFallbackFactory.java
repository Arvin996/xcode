package cn.xk.xcode.factory;

import cn.xk.xcode.client.TakeoutDishClient;
import cn.xk.xcode.entity.dish.TakeoutDishResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collection;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/11/1 16:02
 * @Version 1.0.0
 * @Description TakeoutDishClientFallbackFactory
 **/
public class TakeoutDishClientFallbackFactory implements FallbackFactory<TakeoutDishClient> {
    @Override
    public TakeoutDishClient create(Throwable cause) {
        return new TakeoutDishClient() {
            @Override
            public CommonResult<TakeoutDishResultVo> getDishList() {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<TakeoutDishResultVo> getDish(Long id) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<TakeoutDishResultVo> getDishes(Collection<Long> id) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
