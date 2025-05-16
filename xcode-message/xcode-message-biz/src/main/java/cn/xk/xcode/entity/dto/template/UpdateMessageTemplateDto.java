package cn.xk.xcode.entity.dto.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/5/16 16:14
 * @Version 1.0.0
 * @Description UpdateMessageTemplateDto
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(name = "UpdateMessageTemplateDto", description = "UpdateMessageTemplateDto 更新模板dto")
public class UpdateMessageTemplateDto extends AddMessageTemplateDto{

    @Schema(description = "id")
    @NotNull(message = "id 不能为空")
    private Integer id;
}
