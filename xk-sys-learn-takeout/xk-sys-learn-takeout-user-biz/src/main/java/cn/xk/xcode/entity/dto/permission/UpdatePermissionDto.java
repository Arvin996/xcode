package cn.xk.xcode.entity.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2024/10/30 9:14
 * @Version 1.0.0
 * @Description UpdatePermissionDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "更新角色权限dto")
public class UpdatePermissionDto extends PermissionBaseDto{

    /**
     * 资源名
     */
    @Schema(description = "资源名")
    @NotBlank(message = "资源名不能为空")
    private String code;
}
