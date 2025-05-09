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
 * @since 2025-03-10
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
     * 接入密钥，用于校验合法性
     */
    private String accessToken;

    /**
     * 令牌发送时间
     */
    private LocalDateTime accessTime;

    /**
     * 0启用 1禁用
     */
    private String state;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
