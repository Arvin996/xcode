package cn.xk.xcode.controller;

import cn.xk.xcode.client.PayOrderClient;
import cn.xk.xcode.entity.dto.order.PayCreateOrderDto;
import cn.xk.xcode.entity.vo.order.PayOrderResultVo;
import cn.xk.xcode.pojo.CommonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xuk
 * @Date 2024/9/23 14:33
 * @Version 1.0.0
 * @Description PayOrderController
 **/
@RestController
@RequestMapping("/pay/order")
public class PayOrderController implements PayOrderClient {

    @Override
    public CommonResult<Long> createOrder(PayCreateOrderDto payCreateOrderDto) {
        return null;
    }

    @Override
    public CommonResult<PayOrderResultVo> getCreateOrder(Long orderId) {
        return null;
    }
}
