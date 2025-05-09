package cn.xk.xcode.entity.dto.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/3/10 10:28
 * @Version 1.0.0
 * @Description DelMessageTemplateDto
 **/
@Data
@Schema(description = "del message template dto")
public class DelMessageTemplateDto {

    @Schema(description = "id")
    @NotNull(message = "id not be null")
    private Integer id;
}
