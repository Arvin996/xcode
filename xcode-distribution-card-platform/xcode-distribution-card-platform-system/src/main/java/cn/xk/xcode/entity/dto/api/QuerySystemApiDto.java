package cn.xk.xcode.entity.dto.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/5/12 9:32
 * @Version 1.0.0
 * @Description QuerySystemApiDto
 **/
@Data
@Schema(name = "QuerySystemApiDto", description = "Api查询实体类")
public class QuerySystemApiDto {

    /**
     * 服务所属服务名
     */
    @Schema(description = "服务所属服务名")
    private String productName;

    /**
     * 接口权限标识
     */
    @Schema(description = "接口权限标识")
    private String apiCode;

    /**
     * 接口路径
     */
    @Schema(description = "接口路径")
    private String apiPath;
}
