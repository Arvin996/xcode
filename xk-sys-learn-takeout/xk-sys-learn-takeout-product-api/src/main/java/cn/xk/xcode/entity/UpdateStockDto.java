package cn.xk.xcode.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/6 8:47
 * @Version 1.0.0
 * @Description UpdateStockDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "用户收货地址返回")
public class UpdateStockDto {

    @NotNull(message = "商品id不能为空")
    @Schema(description = "商品id")
    private Long productId;

    @NotNull(message = "商品库存不能为空")
    @Min(value = 1, message = "更新的商品库存量不能小于1")
    @Schema(description = "商品库存")
    private Integer stock;

    @NotNull(message = "更新类型不能为空")
    @Schema(description = "0新增 1减少")
    private Integer type;

}
