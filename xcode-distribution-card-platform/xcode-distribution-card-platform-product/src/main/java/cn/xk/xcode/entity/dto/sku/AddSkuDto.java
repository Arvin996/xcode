package cn.xk.xcode.entity.dto.sku;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author xuk
 * @Date 2025/6/5 8:48
 * @Version 1.0.0
 * @Description AddSkuDto
 **/
@Data
@Schema(name = "AddSkuDto", description = "AddSkuDto 商品sku添加dto")
public class AddSkuDto {

    /**
     * 商品spu_id
     */
    @Schema(description = "spuId 商品spu_id")
    @NotNull(message = "商品spu_id不能为空")
    private Long spuId;

    /**
     * sku编码
     */
    @NotBlank(message = "sku编码不能为空")
    @Schema(description = "skuCode sku编码")
    private String skuCode;

    /**
     * 价格
     */
    @Schema(description = "price 价格")
    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格不能小于0.01")
    private BigDecimal price;

    /**
     * 库存
     */
    @Schema(description = "stock 库存")
    @NotNull(message = "库存不能为空")
    @Min(value = 1, message = "库存不能小于1")
    private Integer stock;

    /**
     * 预警库存
     */
    @Schema(description = "lowStock 预警库存")
    @NotNull(message = "预警库存不能为空")
    @Min(value = 1, message = "预警库存不能小于1")
    private Integer lowStock;

    /**
     * 展示图片
     */
    @Schema(description = "pic 展示图片")
    private String pic;

    /**
     * 单品促销价格
     */
    @Schema(description = "promotionPrice 单品促销价格 新增不填默认就是原价格")
    private BigDecimal promotionPrice;

    @Schema(description = "productSkuAttributeValues 商品sku属性值")
    @NotEmpty(message = "商品sku属性值不能为空")
    private List<ProductSkuAttributeValue> productSkuAttributeValues;

    @Data
    @Schema(name = "ProductSkuAttributeValue", description = "ProductSkuAttributeValue 商品sku属性值")
    public static class ProductSkuAttributeValue {

        @Schema(description = "attributeId 属性id")
        @NotNull(message = "属性id不能为空")
        private Long attributeId;

        @Schema(description = "value 属性值")
        @NotBlank(message = "属性值不能为空")
        private String value;

        @Schema(description = "pic 属性图片")
        private String pic;
    }
}
