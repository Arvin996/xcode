package cn.xk.xcode.entity.dto.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/5/12 13:36
 * @Version 1.0.0
 * @Description QueryMenuDto
 **/
@Data
@Schema(name = "QueryMenuDto", description = "Menu查询实体类")
@Builder
public class QueryMenuDto {

    @Schema(description = "roleId 角色id")
    @NotNull(message = "roleId不能为空")
    private Integer roleId;
}
