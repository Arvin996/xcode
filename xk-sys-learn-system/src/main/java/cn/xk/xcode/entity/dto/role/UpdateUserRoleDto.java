package cn.xk.xcode.entity.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 20:15
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "更新用户角色Dto")
public class UpdateUserRoleDto
{
    @NotNull(message = "用户id不能为空")
    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer userId;

    @Schema(description = "角色列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "角色列表不能为空")
    private List<Integer> roleIds;
}
