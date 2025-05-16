package cn.xk.xcode.entity.po;

import cn.xk.xcode.core.entity.TransPo;
import cn.xk.xcode.entity.DataStringObjectBaseEntity;
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
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("message_channel_account")
public class MessageChannelAccountPo extends DataStringObjectBaseEntity implements Serializable, TransPo {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 渠道账户名称
     */
    private String accountName;

    /**
     * 渠道id
     */
    private Integer channelId;

    /**
     * 账号权重
     */
    private Double weight;

    /**
     * 0 启用 1弃用
     */
    private String status;

    /**
     * 0 未删除 1已删除
     */
    @com.mybatisflex.annotation.Column(isLogicDelete = true)
    private String isDeleted;

}
