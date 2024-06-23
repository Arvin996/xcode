package cn.xk.xcode.entity.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 19:51
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "更新角色dto")
public class UpdateRoleDto extends AddRoleDto
{
    @NotNull
    @Schema(description = "角色id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    /**
     * 0 启用 1未启用
     */
    @Schema(description = "角色状态 0 启用 1未启用")
    private String status;

}
