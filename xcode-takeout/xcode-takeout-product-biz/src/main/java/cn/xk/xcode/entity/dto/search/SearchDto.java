package cn.xk.xcode.entity.dto.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/11/6 10:59
 * @Version 1.0.0
 * @Description SearchDto
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "查询菜品 dto")
public class SearchDto {

    @Schema(description = "菜品或套餐名")
    @NotBlank(message = "查询名称不能为空")
    private String name;
}
