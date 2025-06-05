package cn.xk.xcode.entity.dto.spu;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/6/4 17:14
 * @Version 1.0.0
 * @Description QuerySpuDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "QuerySpuDto", description = "QuerySpuDto 商品spu查询dto")
public class QuerySpuDto extends PageParam {

    @Schema(description = "storeId 商品所属店铺")
    @NotNull(message = "商品所属店铺不能为空")
    private Long storeId;

    /**
     * 商品名称
     */
    @Schema(description = "name 商品名称")
    private String name;

    /**
     * 商品关键词 多个用逗号隔开
     */
    @Schema(description = "keywords 商品关键词 多个用逗号隔开")
    private String keywords;

    /**
     * 是否热卖推荐: 0 默认 1 热卖
     */
    @Schema(description = "recommendHot 是否热卖推荐: 0 默认 1 热卖")
    private String recommendHot = "0";

    /**
     * 是否优惠推荐: 0 默认 1 优选
     */
    @Schema(description = "recommendBenefit 是否优惠推荐: 0 默认 1 优选")
    private String recommendBenefit = "0";

    /**
     * 是否精品推荐: 0 默认 1 精品
     */
    @Schema(description = "recommendBest 是否精品推荐: 0 默认 1 精品")
    private String recommendBest = "0";

    /**
     * 是否新品推荐: 0 默认 1 新品
     */
    @Schema(description = "recommendNew 是否新品推荐: 0 默认 1 新品")
    private String recommendNew = "0";

    /**
     * 是否优品推荐
     */
    @Schema(description = "recommendGood 是否优品推荐")
    private String recommendGood = "0";
}
