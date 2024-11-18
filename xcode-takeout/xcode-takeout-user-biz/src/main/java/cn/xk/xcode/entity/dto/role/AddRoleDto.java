package cn.xk.xcode.entity.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/10/29 16:24
 * @Version 1.0.0
 * @Description AddRoleDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "新增角色dto")
public class AddRoleDto {

    @Schema(description = "角色名")
    @NotBlank(message = "角色名不能为空")
    private String name;
}
