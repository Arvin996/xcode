package cn.xk.xcode.entity.dto.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author xuk
 * @Date 2025/3/10 10:18
 * @Version 1.0.0
 * @Description AddMessageTemplateDto
 **/
@Data
@Schema(description = "add message template dto")
public class AddMessageTemplateDto {

    /**
     * 模板所属用户id
     */
    @Schema(description = "client id")
    @NotNull(message = "client id cannot be null")
    private Integer clientId;

    @Schema(description = "name")
    @NotBlank(message = "name not be blank")
    private String name;

    /**
     * 模板内容信息 使用{}占位符
     */
    @Schema(description = "content")
    @NotBlank(message = "content cannot be null")
    private String content;

}
