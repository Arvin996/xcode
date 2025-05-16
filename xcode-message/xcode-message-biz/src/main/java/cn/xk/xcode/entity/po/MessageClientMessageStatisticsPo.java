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
 * @author Administrator
 * @since 2025-05-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("message_client_message_statistics")
public class MessageClientMessageStatisticsPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 渠道用户
     */
    private Integer clientId;

    /**
     * 渠道id
     */
    private Integer channelId;

    /**
     * 渠道发送账户
     */
    private Integer channelAccount;

    /**
     * 发送信息条数
     */
    private Integer count;

    /**
     * 成功条数
     */
    private Integer successCount;

    /**
     * 失败条数
     */
    private Integer failCount;

    /**
     * 统计时间
     */
    private LocalDateTime createTime;

}
