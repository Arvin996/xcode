package cn.xk.xcode.entity.dto.app;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author xuk
 * @Date 2024/9/26 14:40
 * @Version 1.0.0
 * @Description QueryPayAppDto
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Schema(description = "查询支付应用 dto")
public class QueryPayAppDto {

    /**
     * 应用编号
     */
    @Schema(description = "应用编号")
    private String appCode;

    /**
     * 应用名称
     */
    @Schema(description = "应用名称")
    private String appName;

}
