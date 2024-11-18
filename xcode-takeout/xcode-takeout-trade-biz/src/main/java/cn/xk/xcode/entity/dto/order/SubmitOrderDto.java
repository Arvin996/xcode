package cn.xk.xcode.entity.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/7 14:09
 * @Version 1.0.0
 * @Description SubmitOrderDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "提交购物车订单dto")
public class SubmitOrderDto {

    @NotNull(message = "用户id不能为空")
    @Schema(description = "用户id")
    private Long userId;

    @NotNull(message = "收货地址id不能为空")
    @Schema(description = "收货地址id")
    private Long addressId;

    @Schema(description = "下单备注")
    private String remark;
}
