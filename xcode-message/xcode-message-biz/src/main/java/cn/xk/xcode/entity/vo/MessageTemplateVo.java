package cn.xk.xcode.entity.vo;

import cn.xk.xcode.entity.po.MessageTemplateParamsPo;
import cn.xk.xcode.entity.po.MessageTemplatePo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author xuk
 * @Date 2025/3/10 10:37
 * @Version 1.0.0
 * @Description MessageTemplateVo
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "message template vo")
public class MessageTemplateVo extends MessageTemplatePo {

    private List<MessageTemplateParamsPo> params;
}
