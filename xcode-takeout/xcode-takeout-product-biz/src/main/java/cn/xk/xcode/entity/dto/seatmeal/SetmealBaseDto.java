package cn.xk.xcode.entity.dto.seatmeal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/6 10:33
 * @Version 1.0.0
 * @Description SetmealBaseDto
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "套餐base dto")
public class SetmealBaseDto {

    @Schema(description = "口味id")
    @NotNull(message = "口味id不能为空")
    private Long id;
}
