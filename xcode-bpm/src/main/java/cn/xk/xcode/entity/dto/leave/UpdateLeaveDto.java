package cn.xk.xcode.entity.dto.leave;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/11/21 15:25
 * @Version 1.0.0
 * @Description UpdateLeaveDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "修改oa请假单")
public class UpdateLeaveDto extends AddLeaveDto{

    @Schema(description = "请假id")
    @NotNull(message = "id不能为空")
    private Long id;
}
