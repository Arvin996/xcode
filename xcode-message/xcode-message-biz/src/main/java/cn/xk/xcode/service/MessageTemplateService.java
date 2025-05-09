package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.template.AddMessageTemplateDto;
import cn.xk.xcode.entity.dto.template.QueryMessageTemplateDto;
import cn.xk.xcode.entity.vo.MessageTemplateVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.MessageTemplatePo;

import java.util.List;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-03-10
 */
public interface MessageTemplateService extends IService<MessageTemplatePo> {

    Boolean addMessageTemplate(AddMessageTemplateDto addMessageTemplateDto);

    List<MessageTemplateVo> getMessageTemplate(QueryMessageTemplateDto queryMessageTemplateDto);
}
