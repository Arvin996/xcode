package cn.xk.xcode.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.dto.template.AddMessageTemplateDto;
import cn.xk.xcode.entity.dto.template.QueryMessageTemplateDto;
import cn.xk.xcode.entity.vo.MessageTemplateVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MessageTemplatePo;
import cn.xk.xcode.mapper.MessageTemplateMapper;
import cn.xk.xcode.service.MessageTemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static cn.xk.xcode.config.GlobalMessageConstants.MESSAGE_TEMPLATE_ALREADY_EXISTS;
import static cn.xk.xcode.entity.def.MessageTemplateParamsTableDef.MESSAGE_TEMPLATE_PARAMS_PO;
import static cn.xk.xcode.entity.def.MessageTemplateTableDef.MESSAGE_TEMPLATE_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-03-10
 */
@Service
public class MessageTemplateServiceImpl extends ServiceImpl<MessageTemplateMapper, MessageTemplatePo> implements MessageTemplateService {

    @Override
    public Boolean addMessageTemplate(AddMessageTemplateDto addMessageTemplateDto) {
        String name = addMessageTemplateDto.getName();
        if (this.exists(MESSAGE_TEMPLATE_PO.CLIENT_ID.eq(addMessageTemplateDto.getClientId()).and(MESSAGE_TEMPLATE_PO.NAME.eq(name)))) {
            ExceptionUtil.castServiceException(MESSAGE_TEMPLATE_ALREADY_EXISTS, addMessageTemplateDto.getName());

        }
        MessageTemplatePo messageTemplatePo = new MessageTemplatePo();
        BeanUtils.copyProperties(addMessageTemplateDto, messageTemplatePo);
        return this.save(messageTemplatePo);
    }

    @Override
    public List<MessageTemplateVo> getMessageTemplate(QueryMessageTemplateDto queryMessageTemplateDto) {
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .from(MESSAGE_TEMPLATE_PO)
                .leftJoin(MESSAGE_TEMPLATE_PARAMS_PO)
                .on(MESSAGE_TEMPLATE_PO.ID.eq(MESSAGE_TEMPLATE_PARAMS_PO.TEMPLATE_ID))
                .where("1=1")
                .and(MESSAGE_TEMPLATE_PO.ID.eq(queryMessageTemplateDto.getId()).when(Objects.nonNull(queryMessageTemplateDto.getId())))
                .and(MESSAGE_TEMPLATE_PO.CLIENT_ID.eq(queryMessageTemplateDto.getClientId()).when(Objects.nonNull(queryMessageTemplateDto.getClientId())))
                .and(MESSAGE_TEMPLATE_PO.NAME.like(queryMessageTemplateDto.getName()).when(StrUtil.isNotBlank(queryMessageTemplateDto.getName())));
        return this.listAs(queryWrapper, MessageTemplateVo.class);
    }
}
