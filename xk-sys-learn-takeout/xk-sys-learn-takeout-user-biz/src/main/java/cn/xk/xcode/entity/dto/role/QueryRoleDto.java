package cn.xk.xcode.entity.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/10/29 17:03
 * @Version 1.0.0
 * @Description QueryRoleDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "查询角色dto")
public class QueryRoleDto {

    @Schema(description = "角色id")
    private Integer id;

    @Schema(description = "角色名")
    private String name;
}
