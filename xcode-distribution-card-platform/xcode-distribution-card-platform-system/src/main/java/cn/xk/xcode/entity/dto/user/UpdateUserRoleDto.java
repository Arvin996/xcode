package cn.xk.xcode.entity.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/5/13 11:50
 * @Version 1.0.0
 * @Description UpdateUserRoleDto
 **/
@Data
@Schema(name = "UpdateUserRoleDto", description = "UserRole修改实体类")
public class UpdateUserRoleDto {

    @Schema(description = "username 用户名")
    @NotBlank(message = "username不能为空")
    private String username;

    @Schema(description = "newRoleId 新角色id")
    @NotNull(message = "newRoleId 不能为空")
    private Integer newRoleId;
}
