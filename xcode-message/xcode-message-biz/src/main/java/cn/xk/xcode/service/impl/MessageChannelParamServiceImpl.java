package cn.xk.xcode.service.impl;

import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.dto.param.AddMessageChannelParamDto;
import cn.xk.xcode.entity.dto.param.UpdateMessageChannelParamDto;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.po.MessageChannelParamPo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.mapper.MessageChannelParamMapper;
import cn.xk.xcode.service.MessageChannelAccountParamValueService;
import cn.xk.xcode.service.MessageChannelAccountService;
import cn.xk.xcode.service.MessageChannelParamService;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cn.xk.xcode.config.GlobalMessageConstants.MESSAGE_CHANNEL_PARAM_ALREADY_EXISTS;
import static cn.xk.xcode.config.GlobalMessageConstants.MESSAGE_CHANNEL_PARAM_NOT_EXISTS;
import static cn.xk.xcode.entity.def.MessageChannelAccountParamValueTableDef.MESSAGE_CHANNEL_ACCOUNT_PARAM_VALUE;
import static cn.xk.xcode.entity.def.MessageChannelParamTableDef.MESSAGE_CHANNEL_PARAM;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@Service
public class MessageChannelParamServiceImpl extends ServiceImpl<MessageChannelParamMapper, MessageChannelParamPo> implements MessageChannelParamService {

    @Resource
    private MessageChannelAccountParamValueService messageChannelAccountParamValueService;

    @Resource
    private MessageChannelAccountService messageChannelAccountService;

    @Override
    public Boolean addMessageChannelParam(AddMessageChannelParamDto addMessageChannelParamDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> exists(MESSAGE_CHANNEL_PARAM.NAME.eq(addMessageChannelParamDto.getName())
                .and(MESSAGE_CHANNEL_PARAM.CHANNEL_ID.eq(addMessageChannelParamDto.getChannelId()))))) {
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_PARAM_ALREADY_EXISTS, addMessageChannelParamDto.getName());
        }
        return save(BeanUtil.toBean(addMessageChannelParamDto, MessageChannelParamPo.class));
    }

    @Transactional
    @Override
    public Boolean delMessageChannelParam(Integer id) {
        this.removeById(id);
        return messageChannelAccountParamValueService.remove(MESSAGE_CHANNEL_ACCOUNT_PARAM_VALUE.CHANNEL_PARAM_ID.eq(id));
    }

    @Override
    public Boolean updateMessageChannelParam(UpdateMessageChannelParamDto updateMessageChannelParamDto) {
        MessageChannelParamPo messageChannelParamPo = this.getById(updateMessageChannelParamDto.getId());
        if (ObjectUtil.isNull(messageChannelParamPo)) {
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_PARAM_NOT_EXISTS);
        }
        if (LogicDeleteManager.execWithoutLogicDelete(() -> exists(MESSAGE_CHANNEL_PARAM.NAME.eq(updateMessageChannelParamDto.getName())
                .and(MESSAGE_CHANNEL_PARAM.CHANNEL_ID.eq(messageChannelParamPo.getChannelId()))
                .and(MESSAGE_CHANNEL_PARAM.ID.ne(updateMessageChannelParamDto.getId()))))) {
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_PARAM_ALREADY_EXISTS, updateMessageChannelParamDto.getName());
        }
        return this.updateById(BeanUtil.toBean(updateMessageChannelParamDto, MessageChannelParamPo.class));
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getChannelParamsAndValueForAccount(Integer accountId) {
        MessageChannelAccountPo messageChannelAccountPo = messageChannelAccountService.getById(accountId);
        if (ObjectUtil.isNull(messageChannelAccountPo)) {
            return CollectionUtil.createEmptyMap();
        }
        if (CommonStatusEnum.isEnable(messageChannelAccountPo.getStatus())) {
            return CollectionUtil.createEmptyMap();
        }
        QueryWrapper queryWrapper = QueryWrapper.create()
                .select(MESSAGE_CHANNEL_PARAM.NAME, MESSAGE_CHANNEL_ACCOUNT_PARAM_VALUE.PARAM_VALUE)
                .from(MESSAGE_CHANNEL_PARAM)
                .leftJoin(MESSAGE_CHANNEL_ACCOUNT_PARAM_VALUE)
                .on(MESSAGE_CHANNEL_ACCOUNT_PARAM_VALUE.CHANNEL_PARAM_ID.eq(MESSAGE_CHANNEL_PARAM.ID).and(MESSAGE_CHANNEL_ACCOUNT_PARAM_VALUE.ACCOUNT_ID.eq(accountId)));
        return (Map<String, Object>) this.listAs(queryWrapper, Map.class);
    }
}
