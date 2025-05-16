package cn.xk.xcode.entity.po;

import cn.xk.xcode.entity.DataStringObjectBaseEntity;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.io.Serializable;
import java.time.LocalDateTime;


import lombok.*;

/**
 * 实体类。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("message_channel_param")
public class MessageChannelParamPo extends DataStringObjectBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 渠道id
     */
    private Integer channelId;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 是否必须 0否 1是
     */
    private String required;

    /**
     * 参数描述
     */
    private String desc;

    /**
     * 是否删除 0未删除 1已删除
     */
    @com.mybatisflex.annotation.Column(isLogicDelete = true)
    private String isDeleted;
}
