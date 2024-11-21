package cn.xk.xcode.entity.vo.leave;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/21 13:54
 * @Version 1.0.0
 * @Description FlowOaLeaveResultVo
 **/
@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "流程分页查询dto")
public class FlowOaLeaveResultVo {

    /**
     * 主键
     */
    @Schema(description = "请假id")
    private Long id;

    /**
     * 请假类型
     */
    @Schema(description = "请假类型")
    private Integer type;

    /**
     * 请假原因
     */
    @Schema(description = "请假原因")
    private String reason;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    /**
     * 请假天数
     */
    @Schema(description = "请假天数")
    private Integer day;

    /**
     * 流程实例的id
     */
    @Schema(description = "流程实例的id")
    private Long instanceId;

    /**
     * 节点编码
     */
    @Schema(description = "节点编码")
    private String nodeCode;

    /**
     * 流程节点名称
     */
    @Schema(description = "流程节点名称")
    private String nodeName;

    /**
     * 节点类型（0开始节点 1中间节点 2结束结点 3互斥网关 4并行网关）
     */
    @Schema(description = "节点类型（0开始节点 1中间节点 2结束结点 3互斥网关 4并行网关）")
    private Boolean nodeType;

    /**
     * 流程状态（0待提交 1审批中 2 审批通过 3自动通过 8已完成 9已退回 10失效）
     */
    @Schema(description = "流程状态（0待提交 1审批中 2 审批通过 3自动通过 8已完成 9已退回 10失效）")
    private String flowStatus;

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    private String createUser;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @Schema(description = "更新者")
    private String updateUser;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**抄送人*/
    @Schema(description = "抄送人")
    private List<String> additionalHandler;
}
