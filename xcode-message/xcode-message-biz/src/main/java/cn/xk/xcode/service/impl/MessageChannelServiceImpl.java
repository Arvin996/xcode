package cn.xk.xcode.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.entity.dto.channel.AddMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.DelMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.QueryMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.UpdateMessageChannelDto;
import cn.xk.xcode.entity.vo.MessageChannelVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.MessageChannelAccountService;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MessageChannelPo;
import cn.xk.xcode.mapper.MessageChannelMapper;
import cn.xk.xcode.service.MessageChannelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;
import java.util.Objects;

import static cn.xk.xcode.config.GlobalMessageConstants.*;
import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT_PO;
import static cn.xk.xcode.entity.def.MessageChannelTableDef.MESSAGE_CHANNEL_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-03-10
 */
@Service
public class MessageChannelServiceImpl extends ServiceImpl<MessageChannelMapper, MessageChannelPo> implements MessageChannelService {


    @Resource
    private MessageChannelAccountService messageChannelAccountService;

    @Override
    public Boolean addMessageChannel(AddMessageChannelDto addMessageChannelDto) {
        validate(addMessageChannelDto);
        MessageChannelPo messageChannelPo = new MessageChannelPo();
        messageChannelPo.setName(addMessageChannelDto.getName());
        messageChannelPo.setCode(addMessageChannelDto.getCode());
        return this.save(messageChannelPo);
    }

    @Override
    public Boolean deleteChannel(DelMessageChannelDto delMessageChannelDto) {
        MessageChannelPo messageChannelPo = this.getById(delMessageChannelDto.getId());
        if (Objects.isNull(messageChannelPo)){
            return true;
        }
        if (messageChannelAccountService.count(MESSAGE_CHANNEL_ACCOUNT_PO.CHANNEL_CODE.eq(messageChannelPo.getCode())) > 0) {
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_HAS_ACCOUNT, messageChannelPo.getCode());
        }
        return this.removeById(delMessageChannelDto.getId());
    }

    @Override
    public Boolean updateMessageChannel(UpdateMessageChannelDto updateMessageChannelDto) {
        validate(updateMessageChannelDto);
        MessageChannelPo messageChannelPo = this.getById(updateMessageChannelDto.getId());
        if (Objects.isNull(messageChannelPo)){
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_NOT_EXISTS, updateMessageChannelDto.getName());
        }
        messageChannelPo.setName(updateMessageChannelDto.getName());
        messageChannelPo.setCode(updateMessageChannelDto.getCode());
        return this.updateById(messageChannelPo);
    }

    @Override
    public List<MessageChannelVo> getMessageChannel(QueryMessageChannelDto queryMessageChannelDto) {
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .select(MESSAGE_CHANNEL_PO.ALL_COLUMNS, MESSAGE_CHANNEL_ACCOUNT_PO.ALL_COLUMNS)
                .from(MESSAGE_CHANNEL_PO)
                .leftJoin(MESSAGE_CHANNEL_ACCOUNT_PO)
                .on(MESSAGE_CHANNEL_PO.CODE.eq(MESSAGE_CHANNEL_ACCOUNT_PO.CHANNEL_CODE))
                .where(MESSAGE_CHANNEL_PO.ID.eq(queryMessageChannelDto.getId()).when(Objects.nonNull(queryMessageChannelDto.getId())))
                .and(MESSAGE_CHANNEL_PO.CODE.like(queryMessageChannelDto.getCode()).when(StrUtil.isBlankIfStr(queryMessageChannelDto.getCode())))
                .and(MESSAGE_CHANNEL_PO.NAME.like(queryMessageChannelDto.getName()).when(StrUtil.isBlankIfStr(queryMessageChannelDto.getName())));
        return this.listAs(queryWrapper, MessageChannelVo.class);
    }

    private void validate(UpdateMessageChannelDto updateMessageChannelDto) {
        String name = updateMessageChannelDto.getName();
        String code = updateMessageChannelDto.getCode();
        Integer id = updateMessageChannelDto.getId();
        if (this.exists(MESSAGE_CHANNEL_PO.NAME.eq(name).and(MESSAGE_CHANNEL_PO.ID.ne(id)))) {
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_NAME_ALREADY_EXISTS, name);
        }
        if (this.exists(MESSAGE_CHANNEL_PO.CODE.eq(code).and(MESSAGE_CHANNEL_PO.ID.ne(id)))) {
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_CODE_ALREADY_EXISTS, code);
        }
    }


    private void validate(AddMessageChannelDto addMessageChannelDto) {
        String name = addMessageChannelDto.getName();
        String code = addMessageChannelDto.getCode();
        if (this.exists(QueryWrapper.create().from(MESSAGE_CHANNEL_PO)
                .where(MESSAGE_CHANNEL_PO.NAME.eq(name)))) {
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_NAME_ALREADY_EXISTS, name);
        }
        if (this.exists(QueryWrapper.create().from(MESSAGE_CHANNEL_PO)
                .where(MESSAGE_CHANNEL_PO.CODE.eq(code)))) {
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_CODE_ALREADY_EXISTS, code);
        }
    }
}
