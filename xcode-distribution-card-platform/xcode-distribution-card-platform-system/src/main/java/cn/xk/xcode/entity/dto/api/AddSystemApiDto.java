package cn.xk.xcode.entity.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author xuk
 * @Date 2025/5/12 10:35
 * @Version 1.0.0
 * @Description AddSystemApiDto
 **/
@Data
@Schema(name = "AddSystemApiDto", description = "Api添加实体类")
public class AddSystemApiDto {

    /**
     * 服务所属服务名
     */
    @Schema(description = "服务所属服务名")
    @NotBlank(message = "服务所属服务名不能为空")
    private String productName;

    /**
     * 接口权限标识
     */
    @Schema(description = "接口权限标识")
    @NotBlank(message = "接口权限标识不能为空")
    private String apiCode;

    /**
     * 接口路径
     */
    @Schema(description = "接口路径")
    @NotBlank(message = "接口路径不能为空")
    private String apiPath;

    /**
     * 接口描述
     */
    @Schema(description = "接口描述")
    private String apiDesc;
}
