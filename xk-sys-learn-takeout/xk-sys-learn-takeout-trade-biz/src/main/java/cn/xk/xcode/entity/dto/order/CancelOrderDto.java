package cn.xk.xcode.entity.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/8 13:49
 * @Version 1.0.0
 * @Description CancelOrderDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "取消订单dto")
public class CancelOrderDto {

    @Schema(description = "订单id")
    @NotNull(message = "订单id不能为空")
    private Long id;
}
