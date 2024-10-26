package cn.xk.xcode.controller;

import cn.xk.xcode.client.PayRefundClient;
import cn.xk.xcode.entity.dto.refund.PayCreateRefundDto;
import cn.xk.xcode.entity.vo.refund.PayCreateRefundVo;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.service.PayRefundService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/10/6 14:52
 * @description RefundController
 */
@RequestMapping("/pay")
@RestController
public class PayRefundController implements PayRefundClient {

    @Resource
    private PayRefundService payRefundService;

    @Override
    @PostMapping(PREFIX + "/createRefund")
    public CommonResult<Long> createRefund(@Validated @RequestBody PayCreateRefundDto reqDTO) {
        return CommonResult.success(payRefundService.createRefund(reqDTO));
    }

    @PostMapping(PREFIX + "/getRefund")
    @Override
    public CommonResult<PayCreateRefundVo> getRefund(@RequestParam("refundId") Long refundId) {
        return CommonResult.success(payRefundService.getRefund(refundId));
    }
}
