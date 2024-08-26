package cn.xk.xcode.entity.dto.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 21:22
 * @description
 */
@Data
@NoArgsConstructor
@Schema(description = "修改资源")
public class UpdateRoleResourcesDto {

    @NotNull(message = "角色id不能为空")
    @Schema(description = "角色id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @Schema(description = "资源", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Integer> resourceIds;
}
