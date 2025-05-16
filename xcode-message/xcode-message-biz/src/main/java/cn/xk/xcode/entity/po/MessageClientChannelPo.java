package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实体类。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("message_client_channel")
public class MessageClientChannelPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 接入商id
     */
    private Integer clientId;

    /**
     * 渠道
     */
    private Integer channelId;

}
