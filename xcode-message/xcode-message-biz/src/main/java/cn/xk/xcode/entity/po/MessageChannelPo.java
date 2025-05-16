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
@Table("message_channel")
public class MessageChannelPo extends DataStringObjectBaseEntity implements Serializable, TransPo {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 渠道code 如短信sms 微信小程序等
     */
    private String code;

    /**
     * 渠道名称
     */
    private String name;

    /**
     * 是否支持负载均衡 0支持 1不支持
     */
    private String supportLoadBalance;

    /**
     * 0 启动 1未启用
     */
    private String status;

}
