package cn.xk.xcode.client;

import cn.xk.xcode.entity.dto.order.PayCreateOrderDto;
import cn.xk.xcode.entity.vo.order.PayOrderResultVo;
import cn.xk.xcode.factory.PayOrderClientFallbackFactory;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author xuk
 * @Date 2024/9/13 9:14
 * @Version 1.0.0
 * @Description PayOrderClient
 **/
@FeignClient(name = "xk-sys-learn-pay", fallbackFactory = PayOrderClientFallbackFactory.class)
@Tag(name = "RPC 服务 - 支付下单")
@RequestMapping("/pay/order")
public interface PayOrderClient {

    String PREFIX = "/order";

    @PostMapping(PREFIX + "/createOrder")
    @Operation(summary = "创建支付单")
    CommonResult<Long> createOrder(@Validated @RequestBody PayCreateOrderDto payCreateOrderDto);

    @GetMapping(PREFIX + "/getOrder")
    @Operation(summary = "获取订单")
    CommonResult<PayOrderResultVo> getCreateOrder(@RequestParam("orderId") Long orderId);
}
