package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.template.AddMessageTemplateDto;
import cn.xk.xcode.entity.dto.template.AddMessageTemplateParamsDto;
import cn.xk.xcode.entity.dto.template.QueryMessageTemplateDto;
import cn.xk.xcode.entity.dto.template.UpdateMessageTemplateDto;
import cn.xk.xcode.entity.po.MessageTemplateParamsPo;
import cn.xk.xcode.entity.po.MessageTemplatePo;
import cn.xk.xcode.entity.vo.template.MessageTemplateVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.mapper.MessageTemplateMapper;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.MessageTemplateParamsService;
import cn.xk.xcode.service.MessageTemplateService;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import com.github.pagehelper.PageInfo;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.List;

import static cn.xk.xcode.config.GlobalMessageConstants.*;
import static cn.xk.xcode.entity.def.MessageTemplateParamsTableDef.MESSAGE_TEMPLATE_PARAMS;
import static cn.xk.xcode.entity.def.MessageTemplateTableDef.MESSAGE_TEMPLATE;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@Service
public class MessageTemplateServiceImpl extends ServiceImpl<MessageTemplateMapper, MessageTemplatePo> implements MessageTemplateService {

    @Resource
    private MessageTemplateParamsService messageTemplateParamsService;

    @Resource
    private MessageTemplateMapper messageTemplateMapper;

    @Override
    public Boolean addMessageTemplate(AddMessageTemplateDto addMessageTemplateDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> exists(MESSAGE_TEMPLATE.TEMPLATE_ID.eq(addMessageTemplateDto.getTemplateId())))) {
            ExceptionUtil.castServiceException(MESSAGE_TEMPLATE_ID_HAS_DEFINED, addMessageTemplateDto.getTemplateId());
        }
        if (LogicDeleteManager.execWithoutLogicDelete(() -> exists(MESSAGE_TEMPLATE.NAME.eq(addMessageTemplateDto.getName())))) {
            ExceptionUtil.castServiceException(MASSAGE_TEMPLATE_NAME_ALREADY_EXISTS, addMessageTemplateDto.getName());
        }
        return save(BeanUtil.toBean(addMessageTemplateDto, MessageTemplatePo.class));
    }

    @Transactional
    @Override
    public Boolean delMessageTemplate(Integer id) {
        this.removeById(id);
        return messageTemplateParamsService.remove(MESSAGE_TEMPLATE_PARAMS.TEMPLATE_ID.eq(id));
    }

    @Override
    public Boolean updateMessageTemplate(UpdateMessageTemplateDto updateMessageTemplateDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> exists(MESSAGE_TEMPLATE.TEMPLATE_ID.eq(updateMessageTemplateDto.getTemplateId())
                .and(MESSAGE_TEMPLATE.ID.ne(updateMessageTemplateDto.getId()))))) {
            ExceptionUtil.castServiceException(MESSAGE_TEMPLATE_ID_HAS_DEFINED, updateMessageTemplateDto.getTemplateId());
        }
        if (LogicDeleteManager.execWithoutLogicDelete(() -> exists(MESSAGE_TEMPLATE.NAME.eq(updateMessageTemplateDto.getName())
                .and(MESSAGE_TEMPLATE.ID.ne(updateMessageTemplateDto.getId()))))) {
            ExceptionUtil.castServiceException(MASSAGE_TEMPLATE_NAME_ALREADY_EXISTS, updateMessageTemplateDto.getName());
        }
        return updateById(BeanUtil.toBean(updateMessageTemplateDto, MessageTemplatePo.class));
    }

    @Override
    public PageResult<MessageTemplateVo> getMessageTemplateList(QueryMessageTemplateDto queryMessageTemplateDto) {
        PageUtil.statePage(queryMessageTemplateDto);
        List<MessageTemplateVo> messageTemplateVoList = messageTemplateMapper.getMessageTemplateList(queryMessageTemplateDto);
        return PageUtil.toPageResult(new PageInfo<>(messageTemplateVoList));
    }

    @Override
    public Boolean addMessageTemplateParam(AddMessageTemplateParamsDto addMessageTemplateParamsDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> messageTemplateParamsService.exists(MESSAGE_TEMPLATE_PARAMS.NAME.eq(addMessageTemplateParamsDto.getName())
                .and(MESSAGE_TEMPLATE_PARAMS.TEMPLATE_ID.eq(addMessageTemplateParamsDto.getTemplateId()))))){
            ExceptionUtil.castServiceException(MESSAGE_TEMPLATE_HAS_DEFINED_THIS_PARAM,  addMessageTemplateParamsDto.getName());
        }
        return messageTemplateParamsService.save(BeanUtil.toBean(addMessageTemplateParamsDto, MessageTemplateParamsPo.class));
    }

    @Override
    public Boolean delMessageTemplateParam(Integer id) {
        return messageTemplateParamsService.removeById(id);
    }
}
