package cn.xk.xcode.entity.dto.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Author xuk
 * @Date 2025/5/16 16:37
 * @Version 1.0.0
 * @Description AddMessageTemplateParamsDto
 **/
@Data
@Schema(name = "AddMessageTemplateParamsDto", description = "AddMessageTemplateParamsDto 新增模板参数dto")
public class AddMessageTemplateParamsDto {

    @Schema(description = "模板id")
    private Integer templateId;

    @Schema(description = "参数名称")
    private String name;

    @Schema(description = "参数描述")
    private String desc;
}
