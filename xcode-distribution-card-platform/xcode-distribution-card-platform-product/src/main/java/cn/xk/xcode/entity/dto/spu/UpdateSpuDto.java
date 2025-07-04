package cn.xk.xcode.entity.dto.spu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * @Author xuk
 * @Date 2025/6/4 15:30
 * @Version 1.0.0
 * @Description UpdateSpuDto
 **/
@Data
@Schema(name = "UpdateSpuDto", description = "UpdateSpuDto 商品spu更新dto")
public class UpdateSpuDto {

    @Schema(description = "id 商品spu id")
    private Long id;

    /**
     * 商品名称
     */
    @Schema(description = "name 商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String name;

    /**
     * 商品简介
     */
    @Schema(description = "introduction 商品简介")
    @NotBlank(message = "商品简介不能为空")
    private String introduction;

    /**
     * 商品关键词 多个用逗号隔开
     */
    @Schema(description = "keywords 商品关键词 多个用逗号隔开")
    @NotBlank(message = "商品关键词不能为空")
    private String keywords;

    /**
     * 商品详情
     */
    @Schema(description = "description 商品详情")
    @NotBlank(message = "商品详情不能为空")
    private String description;

    /**
     * 商品sku属性
     */
    @Schema(description = "ProductSpuAttributeList 商品属性")
    @NotEmpty(message = "商品属性不能为空")
    private Set<String> productSpuAttributeList;

    /**
     * 商品轮播图地址
     * 数组，以逗号分隔
     * 最多上传15张
     */
    @Schema(description = "sliderPicUrls 商品轮播图地址 最多上传15张")
    @Size(min = 1, max = 15, message = "商品轮播图地址最多上传15张")
    @NotEmpty(message = "商品轮播图地址不能为空")
    private List<String> sliderPicUrls;

    /**
     * 商品视频
     */
    @Schema(description = "videoUrl 商品视频")
    @NotBlank(message = "商品视频不能为空")
    private String videoUrl;

    /**
     * 是否热卖推荐: 0 默认 1 热卖
     */
    @Schema(description = "recommendHot 是否热卖推荐: 0 默认 1 热卖")
    @NotBlank(message = "是否热卖推荐不能为空")
    private String recommendHot = "0";

    /**
     * 是否优惠推荐: 0 默认 1 优选
     */
    @Schema(description = "recommendBenefit 是否优惠推荐: 0 默认 1 优选")
    @NotBlank(message = "是否优惠推荐不能为空")
    private String recommendBenefit = "0";

    /**
     * 是否精品推荐: 0 默认 1 精品
     */
    @Schema(description = "recommendBest 是否精品推荐: 0 默认 1 精品")
    @NotBlank(message = "是否精品推荐不能为空")
    private String recommendBest = "0";

    /**
     * 是否新品推荐: 0 默认 1 新品
     */
    @Schema(description = "recommendNew 是否新品推荐: 0 默认 1 新品")
    @NotBlank(message = "是否新品推荐不能为空")
    private String recommendNew = "0";

    /**
     * 是否优品推荐
     */
    @Schema(description = "recommendGood 是否优品推荐")
    @NotBlank(message = "是否优品推荐不能为空")
    private String recommendGood = "0";
}
