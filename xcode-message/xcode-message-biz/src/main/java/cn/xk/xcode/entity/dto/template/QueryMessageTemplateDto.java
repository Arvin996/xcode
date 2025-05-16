package cn.xk.xcode.entity.dto.template;

import cn.xk.xcode.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author xuk
 * @Date 2025/3/10 10:31
 * @Version 1.0.0
 * @Description QueryMessageTemplateDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "QueryMessageTemplateDto", description = "QueryMessageTemplateDto 查询消息模板")
public class QueryMessageTemplateDto extends PageParam {

    @Schema(description = "name")
    private String name;

    @Schema(description = "type")
    private String type;

    @Schema(description = "templateId")
    private String templateId;
}
