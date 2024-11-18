package cn.xk.xcode.entity.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/11/4 8:51
 * @Version 1.0.0
 * @Description QueryCategoryDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "查询分类 dto")
public class QueryCategoryDto extends TakeoutCategoryBaseDto{

    /**
     * 类型   1 菜品分类 2 套餐分类
     */
    @Schema(description = "类型 1 菜品分类 2 套餐分类")
    private Integer type;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String name;

}
