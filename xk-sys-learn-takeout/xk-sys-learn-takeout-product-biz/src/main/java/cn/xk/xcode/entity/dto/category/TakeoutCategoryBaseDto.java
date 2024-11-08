package cn.xk.xcode.entity.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/4 8:39
 * @Version 1.0.0
 * @Description TakeoutCategoryBaseDto
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "菜品分类base dto")
public class TakeoutCategoryBaseDto {

    @Schema(description = "分类id")
    @NotNull(message = "分类id不能为空")
    private Long id;
}
