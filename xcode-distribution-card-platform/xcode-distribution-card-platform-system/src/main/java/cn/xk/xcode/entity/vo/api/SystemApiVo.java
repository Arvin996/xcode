package cn.xk.xcode.entity.vo.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/5/12 9:26
 * @Version 1.0.0
 * @Description SystemApiVo
 **/
@Data
@Schema(name = "SystemApiVo", description = "系统api")
public class SystemApiVo {

    /**
     * api id
     */
    @Schema(description = "api id")
    private Integer id;

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

    /**
     * 接口描述
     */
    @Schema(description = "接口描述")
    private String apiDesc;
}
