package cn.xk.xcode.entity.po;

import cn.xk.xcode.entity.DataStringObjectBaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigDecimal;


import lombok.*;

/**
 *  实体类。
 *
 * @author xuk
 * @since 2025-05-30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("product_sku")
public class ProductSkuPo extends DataStringObjectBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 商品spu_id
     */
    private Long spuId;

    /**
     * sku编码
     */
    private String skuCode;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 预警库存
     */
    private Integer lowStock;

    /**
     * 展示图片
     */
    private String pic;

    /**
     * 销量
     */
    private Integer sale;

    /**
     * 0 上架 1下架 2售罄
     */
    private String status;

    /**
     * 00 待审核 10 审核通过 20 审核不通过
     */
    private String auditStatus;

    /**
     * 单品促销价格
     */
    private BigDecimal promotionPrice;

    /**
     * 是否删除 0未删除 1已删除
     */
    @com.mybatisflex.annotation.Column(isLogicDelete = true)
    private String isDeleted;

}
