package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.template.AddMessageTemplateDto;
import cn.xk.xcode.entity.dto.template.AddMessageTemplateParamsDto;
import cn.xk.xcode.entity.dto.template.QueryMessageTemplateDto;
import cn.xk.xcode.entity.dto.template.UpdateMessageTemplateDto;
import cn.xk.xcode.entity.po.MessageTemplatePo;
import cn.xk.xcode.entity.vo.template.MessageTemplateVo;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public interface MessageTemplateService extends IService<MessageTemplatePo> {

    Boolean addMessageTemplate(AddMessageTemplateDto addMessageTemplateDto);

    Boolean delMessageTemplate(Integer id);

    Boolean updateMessageTemplate(UpdateMessageTemplateDto updateMessageTemplateDto);

    PageResult<MessageTemplateVo> getMessageTemplateList(QueryMessageTemplateDto queryMessageTemplateDto);

    Boolean addMessageTemplateParam(AddMessageTemplateParamsDto addMessageTemplateParamsDto);

    Boolean delMessageTemplateParam(Integer id);
}
