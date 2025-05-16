package cn.xk.xcode.entity.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/5/12 15:43
 * @Version 1.0.0
 * @Description SystemRoleVo
 **/
@Data
@Schema(name = "SystemRoleVo", description = "SystemRoleVo 系统角色返回")
public class SystemRoleVo {

    @Schema(description = "id")
    private Integer id;

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
