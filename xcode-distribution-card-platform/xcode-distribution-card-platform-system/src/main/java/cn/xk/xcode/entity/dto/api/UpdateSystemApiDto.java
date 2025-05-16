package cn.xk.xcode.entity.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/5/12 10:48
 * @Version 1.0.0
 * @Description UpdateSystemApiDto
 **/
@Data
@Schema(name = "UpdateSystemApiDto", description = "Api修改实体类")
public class UpdateSystemApiDto {

    @Schema(description = "id 主键")
    @NotNull(message = "id不能为空")
    private Integer id;

    /**
     * 服务所属服务名
     */
    @Schema(description = "productName 服务所属服务名")
    private String productName;

    /**
     * 接口权限标识
     */
    @Schema(description = "apiCode 接口权限标识")
    private String apiCode;

    /**
     * 接口路径
     */
    @Schema(description = "apiPath 接口路径")
    private String apiPath;

    /**
     * 接口描述
     */
    @Schema(description = "apiDesc 接口描述")
    private String apiDesc;
}
