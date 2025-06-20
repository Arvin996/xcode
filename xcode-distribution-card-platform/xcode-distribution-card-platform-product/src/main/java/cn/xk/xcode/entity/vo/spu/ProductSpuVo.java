package cn.xk.xcode.entity.vo.spu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author xuk
 * @Date 2025/6/4 17:12
 * @Version 1.0.0
 * @Description ProductSpuVo
 **/
@Data
@Schema(name = "ProductSpuVo", description = "ProductSpuVo 商品spu vo")
public class ProductSpuVo {

    @Schema(description = "id 商品spu id")
    private Long id;

    /**
     * 商品名称
     */
    @Schema(description = "name 商品名称")
    private String name;

    /**
     * 商品简介
     */
    @Schema(description = "introduction 商品简介")
    private String introduction;

    /**
     * 商品关键词 多个用逗号隔开
     */
    @Schema(description = "keywords 商品关键词 多个用逗号隔开")
    private String keywords;

    /**
     * 商品详情
     */
    @Schema(description = "description 商品详情")
    private String description;

    /**
     * 商品封面图
     */
    @Schema(description = "picUrl 商品封面图")
    private String picUrl;

    /**
     * 商品sku属性
     */
    @Schema(description = "ProductSpuAttributeList 商品属性")
    private Set<String> productSpuAttributeList;

    /**
     * 商品轮播图地址
     * 数组，以逗号分隔
     * 最多上传15张
     */
    @Schema(description = "sliderPicUrls 商品轮播图地址 最多上传15张")
    private List<String> sliderPicUrls;

    /**
     * 商品视频
     */
    @Schema(description = "videoUrl 商品视频")
    private String videoUrl;

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
