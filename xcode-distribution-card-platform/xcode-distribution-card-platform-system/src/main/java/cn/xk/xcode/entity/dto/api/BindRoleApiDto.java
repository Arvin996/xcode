package cn.xk.xcode.entity.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/5/12 11:42
 * @Version 1.0.0
 * @Description BindRoleApiDto
 **/
@Data
@Schema(name = "BindRoleApiDto", description = "Api绑定角色实体类")
public class BindRoleApiDto {

    @Schema(description = "roleId 角色id")
    @NotNull(message = "roleId不能为空")
    private Integer roleId;

    @Schema(description = "apiIdList 接口id列表")
    private List<Integer> apiIdList;
}
