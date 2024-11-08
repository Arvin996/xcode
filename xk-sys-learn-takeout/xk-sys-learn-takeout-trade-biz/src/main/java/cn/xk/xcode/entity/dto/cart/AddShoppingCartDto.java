package cn.xk.xcode.entity.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/11/6 15:56
 * @Version 1.0.0
 * @Description AddShoppingCartDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "添加购物车")
public class AddShoppingCartDto {

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空")
    @Schema(description = "商品名称")
    private String name;

    /**
     * 图片
     */
    @Schema(description = "图片")
    private String image;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    /**
     * 菜品id
     */
    @Schema(description = "菜品id")
    private Long dishId;

    /**
     * 套餐id
     */
    @Schema(description = "套餐id")
    private Long setmealId;

    /**
     * 口味
     */
    @Schema(description = "菜品口味")
    private String dishFlavor;

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空")
    @Schema(description = "数量")
    @Min(value = 1, message = "数量不能小于1")
    private Integer number;

    public boolean isValid() {
        return Objects.nonNull(dishId) || Objects.nonNull(setmealId);
    }
}
