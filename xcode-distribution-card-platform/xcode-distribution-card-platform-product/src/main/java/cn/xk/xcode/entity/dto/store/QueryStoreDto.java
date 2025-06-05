package cn.xk.xcode.entity.dto.store;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/6/4 13:57
 * @Version 1.0.0
 * @Description QueryStoreDto
 **/
@Data
@Schema(name = "QueryStoreDto", description = "QueryStoreDto 店铺查询dto")
public class QueryStoreDto {

    /**
     * 店铺名称
     */
    @Schema(description = "name 店铺名称")
    private String name;

    /**
     * 左区间成交率
     */
    @Schema(description = "minCloseRate 左区间成交率")
    private Double minCloseRate;

    /**
     * 右区间成交率
     */
    @Schema(description = "maxCloseRate 右区间成交率")
    private Double maxCloseRate;

    /**
     * 左区间总销售量
     */
    @Schema(description = "minSaleCount 总销售量")
    private Integer minSaleCount;

    /**
     * 右区间总销售量
     */
    @Schema(description = "maxSaleCount 总销售量")
    private Integer maxSaleCount;

    /**
     * 审核状态 00 待审核 10审核通过 20审核不通过
     */
    @Schema(description = "auditStatus 审核状态 00 待审核 10审核通过 20审核不通过")
    private String auditStatus;

    /**
     * 0 正常 1封禁
     */
    @Schema(description = "status 0 正常 1封禁")
    private String status;
}
