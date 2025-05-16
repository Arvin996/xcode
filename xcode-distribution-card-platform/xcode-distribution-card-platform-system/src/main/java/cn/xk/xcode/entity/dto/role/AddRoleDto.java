package cn.xk.xcode.entity.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/5/12 16:43
 * @Version 1.0.0
 * @Description AddRoleDto
 **/
@Data
@Schema(name = "AddRoleDto", description = "新增role dto")
public class AddRoleDto {

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
