package cn.xk.xcode.entity.vo.store;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/6/4 13:50
 * @Version 1.0.0
 * @Description ProductStoreVo
 **/
@Data
@Schema(name = "ProductStoreVo", description = "ProductStoreVo 店铺vo")
public class ProductStoreVo {

    @Schema(description = "id 店铺id")
    private Long id;

    /**
     * 店铺名称
     */
    @Schema(description = "name 店铺名称")
    private String name;

    /**
     * 店铺简介
     */
    @Schema(description = "desc 店铺简介")
    private String desc;

    /**
     * 店铺logo
     */
    @Schema(description = "logo 店铺logo")
    private String logo;

    /**
     * 成交率
     */
    @Schema(description = "closeRate 成交率")
    private Double closeRate;

    /**
     * 总销售量
     */
    @Schema(description = "saleCount 总销售量")
    private Integer saleCount;

    /**
     * 店铺客服qq
     */
    @Schema(description = "contactQq 店铺客服qq")
    private String contactQq;

    /**
     * 店铺客户微信
     */
    @Schema(description = "contactWx 店铺客户微信")
    private String contactWx;

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
