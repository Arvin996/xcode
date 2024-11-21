package cn.xk.xcode.client;

import cn.xk.xcode.entity.TakeoutOrderResultVo;
import cn.xk.xcode.factory.TakeoutOrderClientFallback;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/6 13:40
 * @Version 1.0.0
 * @Description TakeoutOrderClient
 **/
@FeignClient(value = "xcode-takeout-trade", fallbackFactory = TakeoutOrderClientFallback.class)
@RequestMapping("/takeout/order")
@Tag(name = "订单controller")
public interface TakeoutOrderClient {

    @PostMapping("/getOrder")
    @Operation(summary = "根据id获取订单")
    CommonResult<TakeoutOrderResultVo> getOrder(@RequestParam("orderId") Long orderId);

    @PostMapping("/getOrderList")
    @Operation(summary = "获取用户订单列表")
    CommonResult<List<TakeoutOrderResultVo>> getOrderList(@RequestParam("userId") Long userId);

    @PostMapping("/getUserOrderId")
    @Operation(summary = "获取用户某个订单信息")
    CommonResult<TakeoutOrderResultVo> getUserOrderId(@RequestParam("userId") Long userId, @RequestParam("orderId") Long orderId);
}
