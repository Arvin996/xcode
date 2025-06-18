package cn.xk.xcode.entity.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/6/5 14:03
 * @Version 1.0.0
 * @Description AddCategoryDto
 **/
@Data
@Schema(name = "AddCategoryDto", description = "AddCategoryDto 商品分类添加dto")
public class AddCategoryDto {

    /**
     * 父分类
     */
    @Schema(description = "parentId 父分类 顶级分类不传 默认为0")
    private Long parentId;

    /**
     * 分类名称
     */
    @Schema(description = "name 分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    /**
     * 分类图片
     */
    @Schema(description = "pic 分类图片")
    private String pic;
}

