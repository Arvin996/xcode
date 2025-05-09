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
@Table("message_client_message_statistics")
public class MessageClientMessageStatisticsPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 渠道用户
     */
    private Integer clientId;

    /**
     * 渠道code
     */
    private String channelCode;

    /**
     * 发送信息条数
     */
    private Integer count;

    /**
     * 统计时间
     */
    private LocalDateTime createTime;

}
