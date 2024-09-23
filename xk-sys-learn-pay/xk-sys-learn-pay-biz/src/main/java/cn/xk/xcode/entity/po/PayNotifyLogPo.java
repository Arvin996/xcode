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
 * @since 2024-09-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("pay_notify_log")
public class PayNotifyLogPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日志id
     */
    @Id(keyType = KeyType.Auto)
    private Integer id;

    /**
     * 任务id
     */
    private Integer taskId;

    /**
     * 当前通知次数
     */
    private Integer notifyTimes;

    /**
     * 通知响应结果
     */
    private String response;

    /**
     * 通知结果
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
