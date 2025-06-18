package cn.xk.xcode.entity.vo.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/6/5 15:05
 * @Version 1.0.0
 * @Description ProductCategoryVo
 **/
@Data
@Schema(name = "ProductCategoryVo", description = "ProductCategoryVo 商品分类vo")
public class ProductCategoryVo {

    @Schema(description = "id 分类id")
    private Long id;

    /**
     * 父分类
     */
    @Schema(description = "parentId 父分类")
    private Long parentId;

    /**
     * 分类名称
     */
    @Schema(description = "name 分类名称")
    private String name;

    /**
     * 排序字段
     */
    @Schema(description = "sort 排序字段")
    private Long sort;

    /**
     * 分类图片
     */
    @Schema(description = "pic 分类图片")
    private String pic;

    @Schema(description = "children 子分类")
    private List<ProductCategoryVo> children;
}
