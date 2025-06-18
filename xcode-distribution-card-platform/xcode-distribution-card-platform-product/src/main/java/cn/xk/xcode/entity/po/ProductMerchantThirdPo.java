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
 * 实体类。
 *
 * @author xuk
 * @since 2025-06-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("product_merchant_third")
public class ProductMerchantThirdPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 对应的商户id
     */
    private Long merchantId;

    /**
     * 三方登录方式 如feishu  qq dingding等
     */
    private String type;

    /**
     * 三方平台用户的user_id
     */
    private String unionId;

    /**
     * 是否删除 0未删除 1已删除
     */
    private String isDeleted;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
