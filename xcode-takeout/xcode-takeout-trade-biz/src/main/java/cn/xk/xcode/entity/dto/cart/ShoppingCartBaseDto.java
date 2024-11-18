package cn.xk.xcode.entity.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/7 9:59
 * @Version 1.0.0
 * @Description ShoppingCartBaseDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "购物车base dto")
public class ShoppingCartBaseDto {

    @Schema(description = "购物车明细id")
    @NotNull(message = "购物车明细id不能为空")
    private Long id;

    @Schema(description = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;
}
