package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


import lombok.*;

/**
 *  实体类。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("message_channel_access_client")
public class MessageChannelAccessClientPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 接入商名称
     */
    private String name;

    /**
     * 接入商邮箱
     */
    private String email;

    /**
     * 接入商手机号
     */
    private String mobile;

    /**
     * 接入密钥，用于校验合法性
     */
    private String accessToken;

    /**
     * 0启用 1禁用
     */
    private String status;

    /**
     * 接入商消息配额 默认100
     */
    private Integer accessCount;

    /**
     * 已用配额
     */
    private Integer usedCount;

    /**
     * 剩余配额
     */
    private Integer restCount;

    /**
     * 是否删除 0未删除 1已删除
     */
    @com.mybatisflex.annotation.Column(isLogicDelete = true)
    private String isDelete;

    /**
     * token 刷新时间
     */
    @com.mybatisflex.annotation.Column(onInsertValue = "now()")
    private LocalDateTime tokenRefreshTime;

    /**
     * 创建时间
     */
    @com.mybatisflex.annotation.Column(onInsertValue = "now()")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @com.mybatisflex.annotation.Column(onUpdateValue = "now()")
    private LocalDateTime updateTime;
}
