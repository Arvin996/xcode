package cn.xk.xcode.entity.dto.category;

import cn.xk.xcode.enums.CategoryEnum;
import cn.xk.xcode.validation.InIntEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/1 16:24
 * @Version 1.0.0
 * @Description AddCategoryDto
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "新增菜品分类 dto")
public class AddCategoryDto {

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
