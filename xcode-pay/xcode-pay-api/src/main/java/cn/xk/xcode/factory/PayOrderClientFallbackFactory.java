package cn.xk.xcode.factory;

import cn.xk.xcode.client.PayOrderClient;
import cn.xk.xcode.entity.dto.order.PayCreateOrderDto;
import cn.xk.xcode.entity.vo.order.PayOrderResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/9/13 9:25
 * @Version 1.0.0
 * @Description PayOrderClientFallbackFactory
 **/
public class PayOrderClientFallbackFactory implements FallbackFactory<PayOrderClient> {

    @Override
    public PayOrderClient create(Throwable cause) {
        return new PayOrderClient() {
            @Override
            public CommonResult<Long> createOrder(PayCreateOrderDto payCreateOrderDto) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<PayOrderResultVo> getCreateOrder(Long orderId) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
