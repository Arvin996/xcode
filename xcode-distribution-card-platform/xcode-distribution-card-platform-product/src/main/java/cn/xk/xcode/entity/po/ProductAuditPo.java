package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author xuk
 * @since 2025-05-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("product_audit")
public class ProductAuditPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 商品spu id或者店铺id或者商品sku_id
     */
    private Long bizId;

    /**
     * 00待审核 10 审核通过 20 审核不通过
     */
    private String status;

    /**
     * 审核类型 0 商品spu 1商品sku 2店铺
     */
    private String type;

    /**
     * 审核失败原因
     */
    private String failMsg;

    /**
     * 审核申请人
     */
    private String createUser;

    /**
     * 审核人
     */
    private String auditUser;

    /**
     * 审核时间
     */
    private LocalDateTime auditTime;

    /**
     * 审核创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

}
