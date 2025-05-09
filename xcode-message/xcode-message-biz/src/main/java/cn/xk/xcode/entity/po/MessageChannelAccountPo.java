package cn.xk.xcode.entity.po;

import cn.xk.xcode.entity.DataLongObjectBaseEntity;
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
 * @since 2025-03-10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("message_channel_account")
public class MessageChannelAccountPo extends DataLongObjectBaseEntity implements Serializable {

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
     * 渠道code 
     */
    private String channelCode;

    /**
     * 权重
     */
    private Double weight;

    /**
     * 渠道配置 jsonz字符串 主要配置appid等一些必须的参数
     */
    private String channelConfig;

    /**
     * 0 启用 1弃用
     */
    private String status;

}
