package cn.xk.xcode.entity.dto.template;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/10 10:18
 * @Version 1.0.0
 * @Description AddMessageTemplateDto
 **/
@Data
@Schema(name = "AddMessageTemplateDto", description = "AddMessageTemplateDto 新增消息模板")
public class AddMessageTemplateDto {

    /**
     * 模板名称
     */
    @Schema(description = "模板名称")
    @NotBlank(message = "模板名称不能为空")
    private String name;

    /**
     * 0自定义模板 1 三方平台的模板
     */
    @Schema(description = "0自定义模板 1 三方平台的模板")
    @NotBlank(message = "模板类型不能为空")
    private String type;

    /**
     * 模板id 指的是在三方平台中定义模板后的id值 或者自定义的
     */
    @Schema(description = "模板id 指的是在三方平台中定义模板后的id值 或者自定义的")
    @NotBlank(message = "模板id不能为空")
    private String templateId;

    /**
     * 模板内容信息 使用{}占位符
     */
    @Schema(description = "模板内容信息 使用{}占位符")
    @NotBlank(message = "模板内容不能为空")
    private String content;

    /**
     * 模板描述
     */
    @Schema(description = "模板描述")
    private String desc;
}
