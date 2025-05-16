package cn.xk.xcode.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author xuk
 * @Date 2024/6/24 10:37
 * @Version 1.0
 * @Description LoginUser
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "登录用户")
public class LoginUser {
    @Schema(description = "用户名")
    private String username;

    @Schema(description = "权限标识列表")
    private Set<String> permissions = new HashSet<>();

    @Schema(description = "角色列表")
    private Set<String> roles = new HashSet<>();

    @Schema(description = "菜单路由")
    private Object menus;

    @Schema(description = "用户部门")
    private Set<Long> deptIds;

    @Schema(description = "用户部门及子部门")
    private Set<Long> deptIdsAndChildren;
}
