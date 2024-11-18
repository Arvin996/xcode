package cn.xk.xcode.entity.refund;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/9/3 16:31
 * @Version 1.0.0
 * @Description PayCreateRefundDto 订单退费创建dto
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayCreateRefundDto {

    @NotEmpty(message = "外部订单编号不能为空")
    private String outTradeNo;

    @NotEmpty(message = "退款请求单号不能为空")
    private String outRefundNo;

    /**
     * 退款原因
     */
    @NotBlank(message = "退款原因不能为空")
    private String reason;

    /**
     * 支付金额，单位：分
     * 目前微信支付在退款的时候，必须传递该字段
     */
    @NotNull(message = "支付金额不能为空")
    @DecimalMin(value = "0", inclusive = false, message = "支付金额必须大于零")
    private Integer payPrice;

    /**
     * 退款金额，单位：分
     */
    @NotNull(message = "退款金额不能为空")
    @DecimalMin(value = "0", inclusive = false, message = "支付金额必须大于零")
    private Integer refundPrice;

    /**
     * 退款结果的 notify 回调地址
     */
    @NotBlank(message = "退款结果的回调地址不能为空")
    @URL(message = "退款结果的 notify 回调地址必须是 URL 格式")
    private String notifyUrl;
}
