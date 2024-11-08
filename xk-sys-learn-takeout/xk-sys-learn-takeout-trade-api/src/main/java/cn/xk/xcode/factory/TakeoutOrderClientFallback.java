package cn.xk.xcode.factory;

import cn.xk.xcode.client.TakeoutOrderClient;
import cn.xk.xcode.entity.TakeoutOrderResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.List;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/11/6 14:32
 * @Version 1.0.0
 * @Description TakeoutOrderClientFallback
 **/
public class TakeoutOrderClientFallback implements FallbackFactory<TakeoutOrderClient> {
    @Override
    public TakeoutOrderClient create(Throwable cause) {
        return new TakeoutOrderClient() {
            @Override
            public CommonResult<TakeoutOrderResultVo> getOrder(Long orderId) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<List<TakeoutOrderResultVo>> getOrderList(Long userId) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<TakeoutOrderResultVo> getUserOrderId(Long userId, Long orderId) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
