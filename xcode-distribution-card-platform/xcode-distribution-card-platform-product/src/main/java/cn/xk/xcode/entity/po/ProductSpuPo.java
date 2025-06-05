package cn.xk.xcode.entity.po;

import cn.xk.xcode.entity.DataStringObjectBaseEntity;
import cn.xk.xcode.typehandler.ListStringTypeHandler;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.util.List;


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
@Table("product_spu")
public class ProductSpuPo extends DataStringObjectBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品 SPU 编号，自增
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 商品所属店铺
     */
    private Long storeId;

    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品简介
     */
    private String introduction;

    /**
     * 商品关键词 多个用逗号隔开
     */
    private String keywords;

    /**
     * 商品详情
     */
    private String description;

    /**
     * 商品封面图
     */
    private String picUrl;

    /**
     商品轮播图地址
     数组，以逗号分隔
     最多上传15张
     */
    @Column(typeHandler = ListStringTypeHandler.class)
    private List<String> sliderPicUrls;

    /**
     * 商品视频
     */
    private String videoUrl;

    /**
     * 排序字段
     */
    private Integer sort;

    /**
     * 审核状态 00 待审核 10审核通过 20审核不通过
     */
    private String auditStatus;

    /**
     * 0 上架 1下架
     */
    private String status;

    /**
     * 是否热卖推荐: 0 默认 1 热卖
     */
    private String recommendHot;

    /**
     * 是否优惠推荐: 0 默认 1 优选
     */
    private String recommendBenefit;

    /**
     * 是否精品推荐: 0 默认 1 精品
     */
    private String recommendBest;

    /**
     * 是否新品推荐: 0 默认 1 新品
     */
    private String recommendNew;

    /**
     * 是否优品推荐
     */
    private String recommendGood;

    /**
     * 商品销量
     */
    private Integer salesCount;

    /**
     * 是否删除 0未删除 1已删除
     */
    @com.mybatisflex.annotation.Column(isLogicDelete = true)
    private String isDeleted;

}
