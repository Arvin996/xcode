package cn.xk.xcode.factory;

import cn.xk.xcode.client.PayRefundClient;
import cn.xk.xcode.entity.dto.refund.PayCreateRefundDto;
import cn.xk.xcode.entity.vo.refund.PayCreateRefundVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.cloud.openfeign.FallbackFactory;

import static cn.xk.xcode.exception.GlobalErrorCodeConstants.SERVICE_FALL_BACK;

/**
 * @Author xuk
 * @Date 2024/9/13 9:34
 * @Version 1.0.0
 * @Description PayRefundClientFallbackFactory
 **/
public class PayRefundClientFallbackFactory implements FallbackFactory<PayRefundClient> {

    @Override
    public PayRefundClient create(Throwable cause) {
        return new PayRefundClient() {
            @Override
            public CommonResult<Long> createRefund(PayCreateRefundDto reqDTO) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }

            @Override
            public CommonResult<PayCreateRefundVo> getRefund(Long refundId) {
                return CommonResult.error(SERVICE_FALL_BACK);
            }
        };
    }
}
