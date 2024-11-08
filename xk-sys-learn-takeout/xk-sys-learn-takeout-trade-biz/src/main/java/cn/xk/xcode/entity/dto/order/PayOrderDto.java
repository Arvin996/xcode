package cn.xk.xcode.entity.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/8 13:54
 * @Version 1.0.0
 * @Description PayOrderDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "订单支付dto")
public class PayOrderDto {

    @NotNull(message = "订单号不能为空")
    @Schema(description = "订单id")
    private String orderId;

    @NotNull(message = "支付方式不能为空")
    @Schema(description = "支付方式")
    private Integer payType;
}
