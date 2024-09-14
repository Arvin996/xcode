package cn.xk.xcode.entity.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/9/13 8:52
 * @Version 1.0.0
 * @Description PayOrderResultVo
 **/
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "支付订单result")
public class PayOrderResultVo {

    @Schema(description = "订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "渠道编码", requiredMode = Schema.RequiredMode.REQUIRED, example = "wx_pub") // PayChannelEnum 枚举
    private String channelCode;

    // ========== 商户相关字段 ==========

    @Schema(description = "商户订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "M101") // 例如说，内部系统 A 的订单号。需要保证每个 PayMerchantDO 唯一
    private String merchantOrderId;

    // ========== 订单相关字段 ==========

    @Schema(description = "支付金额，单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "101")
    private Integer price;

    @Schema(description = "支付状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "10") // PayOrderStatusEnum 枚举
    private Integer status;
}
