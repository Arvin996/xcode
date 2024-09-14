package cn.xk.xcode.client;

import cn.xk.xcode.entity.dto.refund.PayCreateRefundDto;
import cn.xk.xcode.entity.vo.refund.PayCreateRefundVo;
import cn.xk.xcode.factory.PayRefundClientFallbackFactory;
import cn.xk.xcode.pojo.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @Author xuk
 * @Date 2024/9/13 9:29
 * @Version 1.0.0
 * @Description PayRefundClient
 **/
@FeignClient(name = "xk-sys-learn-pay", fallbackFactory = PayRefundClientFallbackFactory.class)
@Tag(name = "RPC 服务 - 退款下单")
@RequestMapping("/pay")
public interface PayRefundClient {

    String PREFIX = "/refund";

    @PostMapping(PREFIX + "/createRefund")
    @Operation(summary = "创建退款单")
    CommonResult<Long> createRefund(@Valid @RequestBody PayCreateRefundDto reqDTO);

    @PostMapping(PREFIX + "/getRefund")
    @Operation(summary = "获取退款单")
    CommonResult<PayCreateRefundVo> getRefund(@RequestParam("refundId") Long refundId);
}
