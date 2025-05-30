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
 *  实体类。
 *
 * @author xuk
 * @since 2025-05-30
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("product_merchant")
public class ProductMerchantPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 商户昵称
     */
    private String nickname;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 商户邮箱
     */
    private String email;

    /**
     * 商户头像
     */
    private String avatar;

    /**
     * 账户状态 0 启用 1禁用
     */
    private String status;

    /**
     * 是否删除 0 已删除 1未删除
     */
    @Column(isLogicDelete = true)
    private String isDeleted;

    /**
     * 注册时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime registerTime;

    /**
     * 更新时间
     */
    @Column(onUpdateValue = "now()")
    private LocalDateTime updateTime;

}
