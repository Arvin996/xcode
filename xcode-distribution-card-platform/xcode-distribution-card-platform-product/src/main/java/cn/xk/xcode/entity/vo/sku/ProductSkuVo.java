package cn.xk.xcode.entity.vo.sku;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/6/4 17:13
 * @Version 1.0.0
 * @Description ProductSkuVo
 **/
@Data
@Schema(name = "ProductSkuVo", description = "ProductSkuVo 商品sku vo")
public class ProductSkuVo {

    @Schema(description = "id 商品sku id")
    private Long id;

    /**
     * sku编码
     */
    @Schema(description = "skuCode sku编码")
    private String skuCode;

    /**
     * 价格
     */
    @Schema(description = "price 价格")
    private BigDecimal price;

    /**
     * 库存
     */
    @Schema(description = "stock 库存")
    private Integer stock;

    /**
     * 预警库存
     */
    @Schema(description = "lowStock 预警库存")
    private Integer lowStock;

    /**
     * 展示图片
     */
    @Schema(description = "pic 展示图片")
    private String pic;

    /**
     * 销量
     */
    @Schema(description = "sale 销量")
    private Integer sale;

    /**
     * 0 上架 1下架 2售罄
     */
    @Schema(description = "status 0 上架 1下架 2售罄")
    private String status;

    /**
     * 单品促销价格
     */
    @Schema(description = "promotionPrice 单品促销价格")
    private BigDecimal promotionPrice;

    @Schema(description = "productSkuAttributeValues 商品sku属性值")
    private List<ProductSkuAttributeValueVo> productSkuAttributeValues;


    @Data
    @Schema(name = "ProductSkuAttributeValueVo", description = "ProductSkuAttributeValueVo 商品sku属性值vo")
    public static class ProductSkuAttributeValueVo {

        @Schema(description = "attributeId 属性id")
        private Long attributeId;

        @Schema(description = "attributeName 属性名称")
        private String attributeName;

        @Schema(description = "value 属性值")
        private String value;

        @Schema(description = "pic 属性图片")
        private String pic;

    }
}
