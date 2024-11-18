package cn.xk.xcode.entity.dto.seatmeal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/6 10:50
 * @Version 1.0.0
 * @Description UpdateSetmealDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "更新套餐 dto")
public class UpdateSetmealDto extends AddSetmealDto{

    @Schema(description = "id")
    @NotNull(message = "套餐id不能为空")
    private Long id;
}
