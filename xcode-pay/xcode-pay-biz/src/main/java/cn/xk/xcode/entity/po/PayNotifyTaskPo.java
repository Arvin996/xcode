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
 * @author Administrator
 * @since 2024-09-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("pay_notify_task")
public class PayNotifyTaskPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务id 自增
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 应用编号
     */
    private Long appId;

    /**
     * 通知类型 支付或者退款
     */
    private String notifyType;

    /**
     * 业务id 订单或者退费id
     */
    @Column("bizId")
    private String bizId;

    /**
     * 通知状态
     */
    private Integer status;

    /**
     * 下一次通知时间
     */
    private LocalDateTime nextNotifyTime;

    /**
     * 最后一次执行的时间
     */
    private LocalDateTime lastExecuteTime;

    /**
     * 当前通知次数
     */
    private Long currentNotifyTimes;

    /**
     * 默认通知次数
     */
    private Integer maxNotifyTimes;

    /**
     * 通知地址
     */
    private String notifyUrl;

}
