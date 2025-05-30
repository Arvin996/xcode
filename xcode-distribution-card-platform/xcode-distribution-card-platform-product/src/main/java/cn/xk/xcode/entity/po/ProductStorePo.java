package cn.xk.xcode.entity.po;

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
@Table("product_store")
public class ProductStorePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 所属商户id
     */
    private Long merchantId;

    /**
     * 店铺名称
     */
    private String name;

    /**
     * 店铺简介
     */
    private String desc;

    /**
     * 店铺logo
     */
    private String logo;

    /**
     * 成交率
     */
    private Double closeRate;

    /**
     * 总销售量
     */
    private Integer saleCount;

    /**
     * 店铺客服qq
     */
    private String contactQq;

    /**
     * 店铺客户微信
     */
    private String contactWx;

    /**
     * 审核状态 00 待审核 10审核通过 20审核不通过
     */
    private String auditStatus;

    /**
     * 0 正常 1封禁
     */
    private String status;

    /**
     * 是否删除 0未删除 1已删除
     */
    private String isDeleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateUser;

}
