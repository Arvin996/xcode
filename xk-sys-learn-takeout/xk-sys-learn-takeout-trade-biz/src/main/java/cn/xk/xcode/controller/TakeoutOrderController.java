package cn.xk.xcode.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.xk.xcode.client.TakeoutOrderClient;
import cn.xk.xcode.entity.TakeoutOrderResultVo;
import cn.xk.xcode.entity.dto.order.CancelOrderDto;
import cn.xk.xcode.entity.dto.order.PayOrderDto;
import cn.xk.xcode.entity.dto.order.SubmitOrderDto;
import cn.xk.xcode.entity.vo.order.OrderSubmitResultVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.TakeoutOrdersService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/7 10:40
 * @Version 1.0.0
 * @Description TakeoutOrderController
 **/
@RestController
@RequestMapping("/takeout/order")
public class TakeoutOrderController implements TakeoutOrderClient {

    @Resource
    private TakeoutOrdersService takeoutOrdersService;

    @PostMapping("/getOrder")
    @Override
    public CommonResult<TakeoutOrderResultVo> getOrder(@RequestParam("orderId") Long orderId) {
        return CommonResult.success(takeoutOrdersService.getOrder(orderId));
    }

    @PostMapping("/getOrderList")
    @Override
    public CommonResult<List<TakeoutOrderResultVo>> getOrderList(@RequestParam("userId") Long userId) {
        return CommonResult.success(takeoutOrdersService.getOrderList(userId));
    }

    @PostMapping("/getUserOrderId")
    @Override
    public CommonResult<TakeoutOrderResultVo> getUserOrderId(@RequestParam("userId") Long userId, @RequestParam("orderId") Long orderId) {
        return CommonResult.success(takeoutOrdersService.getUserOrderId(userId, orderId));
    }

    @PostMapping("/submit")
    @Operation(summary = "提交购物车订单")
    @SaCheckPermission
    public CommonResult<OrderSubmitResultVo> submitOrder(@Validated @RequestBody SubmitOrderDto submitOrderDto){
        return CommonResult.success(takeoutOrdersService.submitOrder(submitOrderDto));
    }

    @PostMapping("/cancel")
    @Operation(summary = "取消订单")
    @SaCheckPermission
    public CommonResult<Boolean> cancelOrder(@Validated @RequestBody CancelOrderDto cancelOrderDto){
        return CommonResult.success(takeoutOrdersService.cancelOrder(cancelOrderDto));
    }

    @PostMapping("/pay")
    @Operation(summary = "支付订单")
    @SaCheckPermission
    public CommonResult<Boolean> payOrder(@Validated @RequestBody PayOrderDto payOrderDto){
        return CommonResult.success(takeoutOrdersService.payOrder(payOrderDto));
    }
}
