package cn.xk.xcode.entity.vo.template;

import cn.xk.xcode.entity.po.MessageTemplateParamsPo;
import cn.xk.xcode.entity.po.MessageTemplatePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/10 10:37
 * @Version 1.0.0
 * @Description MessageTemplateVo
 **/
@Data
@Schema(name = "MessageTemplateVo", description = "MessageTemplateVo 消息模板返回vo")
public class MessageTemplateVo {

    @Schema(description = "id")
    private Integer id;

    /**
     * 模板名称
     */
    @Schema(description = "模板名称")
    private String name;

    /**
     * 0自定义模板 1 三方平台的模板
     */
    @Schema(description = "0自定义模板 1 三方平台的模板")
    private String type;

    /**
     * 模板id 指的是在三方平台中定义模板后的id值 或者自定义的
     */
    @Schema(description = "模板id 指的是在三方平台中定义模板后的id值 或者自定义的")
    private String templateId;

    /**
     * 模板内容信息 使用{}占位符
     */
    @Schema(description = "模板内容信息 使用{}占位符")
    private String content;

    /**
     * 模板描述
     */
    @Schema(description = "模板描述")
    private String desc;

    /**
     * 模板状态
     */
    @Schema(description = "0启用 1禁用")
    private String status;

    @Schema(description = "参数列表")
    private List<MessageTemplateParamVo> params;

    @Data
    @Schema(description = "MessageTemplateParamsVo 消息模板参数vo")
    private static class MessageTemplateParamVo {

        @Schema(description = "id")
        private Integer id;

        @Schema(description = "参数名称")
        private String name;

        @Schema(description = "参数描述")
        private String desc;
    }
}
