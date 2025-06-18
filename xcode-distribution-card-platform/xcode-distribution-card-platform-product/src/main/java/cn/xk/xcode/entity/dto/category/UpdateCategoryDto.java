package cn.xk.xcode.entity.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/6/5 14:18
 * @Version 1.0.0
 * @Description UpdateCategoryDto
 **/
@Data
@Schema(name = "UpdateCategoryDto", description = "UpdateCategoryDto 商品分类更新dto")
public class UpdateCategoryDto {


    @Schema(description = "id 分类id")
    @NotNull(message = "分类id不能为空")
    private Long id;

    /**
     * 分类名称
     */
    @Schema(description = "name 分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    /**
     * 排序字段
     */
    @Schema(description = "sort 排序字段 不传默认是id")
    @NotNull(message = "排序字段不能为空")
    private Long sort;

    /**
     * 分类图片
     */
    @Schema(description = "pic 分类图片")
    private String pic;
}
