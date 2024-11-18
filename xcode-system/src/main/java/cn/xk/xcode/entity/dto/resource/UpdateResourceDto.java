package cn.xk.xcode.entity.dto.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 21:10
 * @description
 */
@Data
@NoArgsConstructor
@Schema(description = "修改资源")
public class UpdateResourceDto {

    @NotNull(message = "资源id不能为空")
    @Schema(description = "资源id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @Schema(description = "资源路径", requiredMode = Schema.RequiredMode.AUTO)
    @NotBlank(message = "资源路径不能为空")
    private String recourseCode;

    @Schema(description = "资源名称")
    private String resourceName;

    @Schema(description = "资源状态")
    private String status;
}
