package cn.xk.xcode.entity.dto.resource;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/6/21 21:07
 * @description
 */
@Data
@NoArgsConstructor
@Schema(description = "新增资源")
public class AddResourceDto {

    @Schema(description = "资源路径", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "资源Code cannot be null")
    private String recourseCode;

    @Schema(description = "资源名称")
    @NotBlank(message = "资源Name不能为空")
    private String resourceName;
}
