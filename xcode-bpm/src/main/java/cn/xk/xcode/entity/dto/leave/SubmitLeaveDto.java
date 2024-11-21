package cn.xk.xcode.entity.dto.leave;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/21 15:52
 * @Version 1.0.0
 * @Description SubmitLeaveDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "提交请假审批")
public class SubmitLeaveDto {

    @NotNull(message = "id不能为空")
    @Schema(description = "请假id")
    private Long id;

    @Schema(description = "流程状态")
    private String flowStatus;
}
