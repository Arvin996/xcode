package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.channel.AddMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.QueryMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.UpdateMessageChannelDto;
import cn.xk.xcode.entity.po.MessageChannelPo;
import cn.xk.xcode.entity.vo.channel.MessageChannelVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.mapper.MessageChannelMapper;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.MessageChannelAccountService;
import cn.xk.xcode.service.MessageChannelParamService;
import cn.xk.xcode.service.MessageChannelService;
import cn.xk.xcode.service.MessageClientChannelService;
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
import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT;
import static cn.xk.xcode.entity.def.MessageChannelParamTableDef.MESSAGE_CHANNEL_PARAM;
import static cn.xk.xcode.entity.def.MessageChannelTableDef.MESSAGE_CHANNEL;
import static cn.xk.xcode.entity.def.MessageClientChannelTableDef.MESSAGE_CLIENT_CHANNEL;

/**
 *  服务层实现。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@Service
public class MessageChannelServiceImpl extends ServiceImpl<MessageChannelMapper, MessageChannelPo> implements MessageChannelService {

    @Resource
    private MessageChannelAccountService messageChannelAccountService;

    @Resource
    private MessageChannelParamService messageChannelParamService;

    @Resource
    private MessageClientChannelService messageClientChannelService;

    @Resource
    private MessageChannelMapper messageChannelMapper;

    @Override
    public Boolean addMessageChannel(AddMessageChannelDto addMessageChannelDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> exists(MESSAGE_CHANNEL.CODE.eq(addMessageChannelDto.getCode())))) {
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_CODE_ALREADY_EXISTS, addMessageChannelDto.getCode());
        }
        return save(BeanUtil.toBean(addMessageChannelDto, MessageChannelPo.class));
    }

    @Transactional
    @Override
    public Boolean deleteChannel(Integer id) {
        if (messageChannelAccountService.exists(MESSAGE_CHANNEL_ACCOUNT.CHANNEL_ID.eq(id))){
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_HAS_BIND_ACCOUNT);
        }
        if (messageChannelParamService.exists(MESSAGE_CHANNEL_PARAM.CHANNEL_ID.eq(id))){
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_HAS_BIND_PARAM);
        }
        this.removeById(id);
        return messageClientChannelService.remove(MESSAGE_CLIENT_CHANNEL.CHANNEL_ID.eq(id));
    }

    @Override
    public Boolean updateMessageChannel(UpdateMessageChannelDto updateMessageChannelDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> exists(MESSAGE_CHANNEL.CODE.eq(updateMessageChannelDto.getCode()).and(MESSAGE_CHANNEL.ID.ne(updateMessageChannelDto.getId()))))) {
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_CODE_ALREADY_EXISTS, updateMessageChannelDto.getCode());
        }
        return updateById(BeanUtil.toBean(updateMessageChannelDto, MessageChannelPo.class));
    }

    @Override
    public PageResult<MessageChannelVo> getMessageChannel(QueryMessageChannelDto queryMessageChannelDto) {
        PageUtil.statePage(queryMessageChannelDto);
        List<MessageChannelVo> messageChannelVoList = messageChannelMapper.getMessageChannel(queryMessageChannelDto);
        return PageUtil.toPageResult(new PageInfo<>(messageChannelVoList));
    }
}
