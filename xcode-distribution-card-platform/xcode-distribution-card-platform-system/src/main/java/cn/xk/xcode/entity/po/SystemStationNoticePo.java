package cn.xk.xcode.entity.po;

import com.mybatisflex.annotation.Column;
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
 * @since 2025-05-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("system_station_notice")
public class SystemStationNoticePo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 消息title
     */
    private String title;

    /**
     * 接收人
     */
    private String toUser;

    /**
     * 消息正文
     */
    private String message;

    /**
     * 0 未读 1已读
     */
    private String isRead;

    /**
     * 通知类型 0公告 1通知
     */
    private String type;

    /**
     * 发送时间
     */
    @Column(onInsertValue = "now()")
    private LocalDateTime createTime;

}
