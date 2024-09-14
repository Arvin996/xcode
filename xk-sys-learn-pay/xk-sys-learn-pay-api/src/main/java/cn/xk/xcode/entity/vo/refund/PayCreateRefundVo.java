package cn.xk.xcode.entity.vo.refund;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2024/9/13 8:57
 * @Version 1.0.0
 * @Description PayCreateRefundVo
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "退款订单result")
public class PayCreateRefundVo {

    @Schema(description = "退款单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    // ========== 退款相关字段 ==========

    @Schema(description = "退款状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "10") // PayRefundStatusEnum 枚举
    private Integer status;

    @Schema(description = "退款金额，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "101")
    private Integer refundPrice;

    // ========== 商户相关字段 ==========

    @Schema(description = "商户订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "M101") // 例如说，内部系统 A 的订单号。需要保证每个 PayMerchantDO 唯一
    private String merchantOrderId;

    @Schema(description = "退款成功时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime successTime;
}
