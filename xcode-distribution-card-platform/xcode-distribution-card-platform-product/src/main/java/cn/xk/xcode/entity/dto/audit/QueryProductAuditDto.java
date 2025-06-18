package cn.xk.xcode.entity.dto.audit;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2025/6/5 15:59
 * @Version 1.0.0
 * @Description QueryProductAudit
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "QueryProductAuditDto", description = "QueryProductAuditDto 商品审核查询dto")
public class QueryProductAuditDto extends PageParam {

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
     * 审核申请人
     */
    @Schema(description = "审核申请人")
    private String createUser;

    /**
     * 审核创建开始时间
     */
    @Schema(description = "审核创建开始时间")
    private LocalDateTime startTime;

    /**
     * 审核创建结束时间
     */
    @Schema(description = "审核创建结束时间")
    private LocalDateTime endTime;
}
