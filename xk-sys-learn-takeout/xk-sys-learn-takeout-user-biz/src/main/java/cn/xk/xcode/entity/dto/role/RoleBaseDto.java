package cn.xk.xcode.entity.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2024/10/29 16:24
 * @Version 1.0.0
 * @Description RoleBaseDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "角色基础dto")
public class RoleBaseDto {

    @Schema(description = "角色id")
    @NotNull(message = "角色id不能为空")
    private Integer id;
}
