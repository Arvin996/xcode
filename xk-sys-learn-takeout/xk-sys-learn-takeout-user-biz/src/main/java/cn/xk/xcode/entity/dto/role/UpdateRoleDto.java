package cn.xk.xcode.entity.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/10/29 16:50
 * @Version 1.0.0
 * @Description UpdateRoleDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "更新角色dto")
public class UpdateRoleDto extends RoleBaseDto {

    @Schema(description = "角色名")
    @NotBlank(message = "角色名不能为空")
    private String name;
}
