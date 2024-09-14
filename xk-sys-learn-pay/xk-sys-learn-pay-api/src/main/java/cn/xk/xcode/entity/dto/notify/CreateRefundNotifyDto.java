package cn.xk.xcode.entity.dto.notify;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/9/12 17:20
 * @Version 1.0.0
 * @Description CreateRefundNotifyDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "退费回调dto")
@Accessors(chain = true)
public class CreateRefundNotifyDto {

    @Schema(description = "商户退款单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "MR101")
    @NotEmpty(message = "商户退款单编号不能为空")
    private String merchantOrderId;

    @Schema(description = "支付订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "R303")
    @NotNull(message = "支付退款编号不能为空")
    private Long payRefundId;
}
