package cn.xk.xcode.entity.refund;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author xuk
 * @Date 2024/9/3 16:31
 * @Version 1.0.0
 * @Description PayCreateRefundDto 订单退费创建dto
 **/
@Data
public class PayCreateRefundDto {

    @NotEmpty(message = "外部订单编号不能为空")
    private String outTradeNo;

    @NotEmpty(message = "退款请求单号不能为空")
    private String outRefundNo;
}
