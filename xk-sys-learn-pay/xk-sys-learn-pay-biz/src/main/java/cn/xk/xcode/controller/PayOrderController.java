package cn.xk.xcode.controller;

import cn.xk.xcode.client.PayOrderClient;
import cn.xk.xcode.entity.dto.order.PayCreateOrderDto;
import cn.xk.xcode.entity.dto.order.PayOrderSubmitReqDto;
import cn.xk.xcode.entity.vo.order.PayOrderRespVo;
import cn.xk.xcode.entity.vo.order.PayOrderResultVo;
import cn.xk.xcode.entity.vo.order.PayOrderSubmitRespVO;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.PayOrderService;
import cn.xk.xcode.utils.object.BeanUtil;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author xuk
 * @Date 2024/9/23 14:33
 * @Version 1.0.0
 * @Description PayOrderController
 **/
@RestController
@RequestMapping("/pay/order")
public class PayOrderController implements PayOrderClient {

    @Resource
    private PayOrderService payOrderService;

    @Override
    public CommonResult<Long> createOrder(@Validated @RequestBody PayCreateOrderDto payCreateOrderDto) {
        return CommonResult.success(payOrderService.createOrder(payCreateOrderDto));
    }

    @Override
    public CommonResult<PayOrderResultVo> getCreateOrder(@RequestParam("orderId") Long orderId) {
        return CommonResult.success(payOrderService.getCreateOrder(orderId));
    }

    @Operation(summary = "获取订单")
    @GetMapping("/get")
    public CommonResult<PayOrderRespVo> getOrder(@RequestParam("id") Long id){
        return CommonResult.success(BeanUtil.toBean(payOrderService.getById(id), PayOrderRespVo.class));
    }

    @Operation(summary = "提交订单")
    @PostMapping("/submit")
    public CommonResult<PayOrderSubmitRespVO> submitOrder(@Validated @RequestBody PayOrderSubmitReqDto payOrderSubmitReqDto){
        return CommonResult.success(payOrderService.submitOrder(payOrderSubmitReqDto));
    }
}
