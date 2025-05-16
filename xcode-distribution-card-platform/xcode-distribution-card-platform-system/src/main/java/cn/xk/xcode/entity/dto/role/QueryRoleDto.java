package cn.xk.xcode.entity.dto.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/5/12 15:45
 * @Version 1.0.0
 * @Description QueryRoleDto
 **/
@Data
@Schema(name = "QueryRoleDto", description = "Role查询实体类")
public class QueryRoleDto {

    /**
     * 角色权限名
     */
    @Schema(description = "角色权限名")
    private String code;

    /**
     * 系统角色名
     */
    @Schema(description = "系统角色名")
    private String name;
}
