package cn.xk.xcode.entity.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/5/12 17:12
 * @Version 1.0.0
 * @Description UpdateRoleDto
 **/
@Schema(name = "UpdateRoleDto", description = "Role修改实体类")
@Data
public class UpdateRoleDto {

    @Schema(description = "角色id")
    @NotNull(message = "id不能为空")
    private Integer id;

    /**
     * 角色权限名
     */
    @Schema(description = "角色权限名")
    @NotBlank(message = "角色权限名不能为空")
    private String code;

    /**
     * 系统角色名
     */
    @Schema(description = "系统角色名")
    @NotBlank(message = "系统角色名不能为空")
    private String name;
}
