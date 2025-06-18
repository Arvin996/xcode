package cn.xk.xcode.entity.vo.audit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2025/6/5 15:56
 * @Version 1.0.0
 * @Description ProductAuditVo
 **/
@Data
@Schema(name = "ProductAuditVo", description = "ProductAuditVo 商品审核vo")
public class ProductAuditVo {

    /**
     * 审核id
     */
    @Schema(description = "审核id")
    private Long id;

    /**
     * 商品spu id或者店铺id或者商品sku_id
     */
    @Schema(description = "商品spu id或者店铺id或者商品sku_id")
    private Long bizId;

    /**
     * 00待审核 10 审核通过 20 审核不通过
     */
    @Schema(description = "00待审核 10 审核通过 20 审核不通过")
    private String status;

    /**
     * 审核类型 0 商品spu 1商品sku 2店铺
     */
    @Schema(description = "审核类型 0 商品spu 1商品sku 2店铺")
    private String type;

    /**
     * 审核失败原因
     */
    @Schema(description = "审核失败原因")
    private String failMsg;

    @Schema(description = "审核数据")
    private Object auditData;

    /**
     * 审核申请人
     */
    @Schema(description = "审核申请人")
    private String createUser;

    /**
     * 审核创建时间
     */
    @Schema(description = "审核创建时间")
    private LocalDateTime createTime;
}
