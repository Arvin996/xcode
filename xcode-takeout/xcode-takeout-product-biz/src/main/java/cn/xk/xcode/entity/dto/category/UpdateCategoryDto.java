package cn.xk.xcode.entity.dto.category;

import cn.xk.xcode.enums.CategoryEnum;
import cn.xk.xcode.validation.InIntEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/4 8:47
 * @Version 1.0.0
 * @Description UpdateCategoryDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "菜品分类base dto")
public class UpdateCategoryDto extends TakeoutCategoryBaseDto{

    /**
     * 类型   1 菜品分类 2 套餐分类
     */
    @Schema(description = "类型 1 菜品分类 2 套餐分类")
    @NotNull(message = "类型不能为空")
    @InIntEnum(value = CategoryEnum.class, message = "类型范围必须是{1, 2}")
    private Integer type;

    /**
     * 分类名称
     */
    @Schema(description = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @Schema(description = "排序")
    private Integer sort;
}
