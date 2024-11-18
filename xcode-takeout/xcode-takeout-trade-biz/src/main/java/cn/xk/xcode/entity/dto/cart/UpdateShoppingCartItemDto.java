package cn.xk.xcode.entity.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/7 10:05
 * @Version 1.0.0
 * @Description UpdateShoppingCartItemDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "更新购物车中商品信息 dto")
public class UpdateShoppingCartItemDto extends ShoppingCartBaseDto{

    /**
     * 口味
     */
    @NotBlank(message = "菜品口味不能为空")
    @Schema(description = "菜品口味")
    private String dishFlavor;

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空")
    @Schema(description = "数量")
    @Min(value = 1, message = "数量不能小于1")
    private Integer number;
}
