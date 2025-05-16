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
@Table("message_task_detail")
public class MessageTaskDetailPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 任务id
     */
    private Long taskId;

    /**
     * 接收人
     */
    private String receiver;

    /**
     * 执行状态 0成功 1失败
     */
    private String status;

    /**
     * 重试次数
     */
    private Integer retryTimes;

    /**
     * 失败原因
     */
    private String failMsg;

    /**
     * 执行时间
     */
    private LocalDateTime execTime;

    /**
     * 成功时间
     */
    private LocalDateTime successTime;

}
