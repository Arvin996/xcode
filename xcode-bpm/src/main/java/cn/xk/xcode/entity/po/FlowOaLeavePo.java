package cn.xk.xcode.entity.po;

import cn.xk.xcode.entity.DataLongObjectBaseEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;


import lombok.*;

/**
 *  实体类。
 *
 * @author xuk
 * @since 2024-11-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("flow_oa_leave")
public class FlowOaLeavePo extends DataLongObjectBaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 请假类型
     */
    private Integer type;

    /**
     * 请假原因
     */
    private String reason;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 请假天数
     */
    private Integer day;

    /**
     * 流程实例的id
     */
    private Long instanceId;

    /**
     * 节点编码
     */
    private String nodeCode;

    /**
     * 流程节点名称
     */
    private String nodeName;

    /**
     * 节点类型（0开始节点 1中间节点 2结束结点 3互斥网关 4并行网关）
     */
    private Integer nodeType;

    /**
     * 流程状态（0待提交 1审批中 2 审批通过 3自动通过 8已完成 9已退回 10失效）
     */
    private String flowStatus;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @Column(isLogicDelete = true)
    private String delFlag;

}
