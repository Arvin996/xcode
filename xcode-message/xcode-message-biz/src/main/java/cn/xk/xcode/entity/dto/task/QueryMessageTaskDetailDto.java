package cn.xk.xcode.entity.dto.task;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author xukai
 * @version 1.0
 * @date 2025/5/18 11:01
 * @description QueryMessageTaskDetailDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "QueryMessageTaskDetailDto", description = "QueryMessageTaskDetailDto 查询任务详情dto")
public class QueryMessageTaskDetailDto extends PageParam {

    @NotNull(message = "任务id不能为空")
    @Schema(description = "任务id")
    private Long taskId;

    /**
     * 执行状态 0成功 1失败
     */
    @Schema(description = "执行状态 0成功 1失败")
    private String status;

    /**
     * 重试次数
     */
    @Schema(description = "重试次数")
    private Integer retryTimes;


    /**
     * 执行开始时间
     */
    private LocalDateTime startTime;

    /**
     * 执行结束时间
     */
    private LocalDateTime endTime;
}
