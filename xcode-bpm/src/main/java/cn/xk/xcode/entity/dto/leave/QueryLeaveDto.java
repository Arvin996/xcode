package cn.xk.xcode.entity.dto.leave;

import cn.xk.xcode.pojo.PageParam;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author xuk
 * @Date 2024/11/21 13:41
 * @Version 1.0.0
 * @Description QueryLeaveDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "请假分页查询dto")
public class QueryLeaveDto extends PageParam {

    /**
     * 请假类型
     */
    @Schema(description = "请假类型")
    private Integer type;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    /**
     * 请假天数
     */
    @Schema(description = "请假天数")
    private Integer day;


    /**
     * 流程状态（0待提交 1审批中 2 审批通过 3自动通过 8已完成 9已退回 10失效）
     */
    @Schema(description = "流程状态（0待提交 1审批中 2 审批通过 3自动通过 8已完成 9已退回 10失效）")
    private String flowStatus;


    /**
     * 请假人
     */
    @Schema(description = "请假人")
    private Long createUser;
}
