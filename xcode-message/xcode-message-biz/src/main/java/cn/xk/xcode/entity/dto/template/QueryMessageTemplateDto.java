package cn.xk.xcode.entity.dto.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/3/10 10:31
 * @Version 1.0.0
 * @Description QueryMessageTemplateDto
 **/
@Data
@Schema(description = "query message template dto")
public class QueryMessageTemplateDto {

    @Schema(description = "id")
    private Integer id;

    @Schema(description = "client id")
    private Integer clientId;

    @Schema(description = "name")
    private String name;
}
