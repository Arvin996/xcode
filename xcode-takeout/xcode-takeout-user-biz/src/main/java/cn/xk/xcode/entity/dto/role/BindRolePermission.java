package cn.xk.xcode.entity.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author xuk
 * @Date 2024/10/30 8:53
 * @Version 1.0.0
 * @Description BindRolePermission
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "绑定角色权限dto")
public class BindRolePermission {

    @Schema(description = "角色id")
    @NotNull(message = "角色id不能为空")
    private Integer roleId;

    @Schema(description = "权限id列表")
    private List<Integer> permissionList;
}
