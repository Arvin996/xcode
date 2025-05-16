package cn.xk.xcode.entity.po;

import cn.xk.xcode.entity.DataStringObjectBaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


import lombok.*;

/**
 *  实体类。
 *
 * @author xuk
 * @since 2025-05-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("system_user")
public class SystemUserPo extends DataStringObjectBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 登录用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 0 男  1女 2未知
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮件
     */
    private String email;

    /**
     * dingTalkWebhookToken
     */
    @Column("ding_talk_webhook_token")
    private String dingTalkWebhookToken;

    /**
     * dingTalkWebhookSign 签名
     */
    @Column("ding_talk_webhook_sign")
    private String dingTalkWebhookSign;


    /**
     * feiShuWebhookToken
     */
    @Column("feishu_webhook_token")
    private String feiShuWebhookToken;

    /**
     * feiShuWebhookSign 签名
     */
    @Column("feishu_webhook_sign")
    private String feiShuWebhookSign;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账号状态 0正常 1停用
     */
    private String status;

    /**
     * 最后登录ip
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 是否删除 0未删除 1已删除
     */
    @Column(isLogicDelete = true)
    private String isDeleted;


}
