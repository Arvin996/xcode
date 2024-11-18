package cn.xk.xcode.entity.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/10/30 9:08
 * @Version 1.0.0
 * @Description PermissionBaseDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "角色权限base dto")
public class PermissionBaseDto {

    @Schema(description = "权限id")
    @NotNull(message = "权限id不能为空")
    private Integer id;
}
