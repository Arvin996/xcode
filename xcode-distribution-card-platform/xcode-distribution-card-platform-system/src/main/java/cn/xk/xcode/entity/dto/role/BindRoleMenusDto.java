package cn.xk.xcode.entity.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/12 17:17
 * @Version 1.0.0
 * @Description BindRoleMenusDto
 **/
@Data
@Schema(name = "BindRoleMenusDto", description = "RoleMenu绑定实体类")
public class BindRoleMenusDto {

    @Schema(description = "roleId 角色id")
    @NotNull(message = "角色id不能为空")
    private Integer roleId;

    @Schema(description = "menuIds 菜单id列表")
    private List<Long> menuIds;
}
