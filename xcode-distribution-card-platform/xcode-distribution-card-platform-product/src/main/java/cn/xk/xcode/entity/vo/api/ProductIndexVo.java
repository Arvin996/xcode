package cn.xk.xcode.entity.vo.api;

import cn.xk.xcode.entity.vo.sku.ProductSkuVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/6/6 16:18
 * @Version 1.0.0
 * @Description ProductIndexVo
 **/
@Data
@Schema(name = "ProductIndexVo", description = "ProductIndexVo 商品首页vo")
public class ProductIndexVo {

    private List<ProductCategoryNumVo> categoryNumVoList = new ArrayList<>();

    private List<ProductSkuVo> weeklyBestSkuList = new ArrayList<>();


    @Data
    @Schema(name = "ProductCategoryNumVo", description = "ProductCategoryNumVo 商品分类数量vo")
    public static class ProductCategoryNumVo {
        @Schema(description = "商品分类id")
        private Long categoryId;
        @Schema(description = "商品分类下的spu数量")
        private Integer spuCount;
        @Schema(description = "商品分类名称")
        private String categoryName;
    }
}
