package cn.xk.xcode.entity.dto.dish;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/11/4 13:29
 * @Version 1.0.0
 * @Description DishBaseDto
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "菜品base dto")
public class DishBaseDto {

    @Schema(description = "菜品id")
    private Long id;
}
