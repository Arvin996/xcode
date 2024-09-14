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
 * @Date 2024/9/12 17:02
 * @Version 1.0.0
 * @Description CreateOrderNotifyDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "支付回调dto")
@Accessors(chain = true)
public class CreateOrderNotifyDto {

    @Schema(description = "商户订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "M101")
    @NotEmpty(message = "商户订单号不能为空")
    private String merchantOrderId;

    @Schema(description = "支付订单编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "P202")
    @NotNull(message = "支付订单编号不能为空")
    private Long payOrderId;
}
