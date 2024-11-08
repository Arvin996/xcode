package cn.xk.xcode.entity.dto.cart;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/7 10:34
 * @Version 1.0.0
 * @Description ClearShoppingCartDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "清空购物车")
public class ClearShoppingCartDto {

    @NotNull(message = "用户id不能为空")
    @Schema(description = "用户id")
    private Long userId;
}
