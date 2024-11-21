package cn.xk.xcode.entity.vo.task;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/11/21 9:38
 * @Version 1.0.0
 * @Description FlowTaskInteractiveTypeVo
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "处理非办理的流程交互类型vo")
public class FlowTaskInteractiveTypeVo extends PageParam {

    /**
     * 任务id
     */
    @Schema(description = "任务id")
    @NotNull(message = "任务id不能为空")
    private Long taskId;

    /**
     * 增加办理人
     */
    @Schema(description = "增加办理人")
    private List<String> addHandlers;

    /**
     * 操作类型[2:转办,6:加签,3:委派,7:减签]
     */
    @NotNull(message = "操作类型不能为空")
    @Schema(description = "操作类型[2:转办,6:加签,3:委派,7:减签]")
    private Integer operatorType;

    @Schema(description = "用户id")
    private List<String> userIds;
}
