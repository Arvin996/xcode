package cn.xk.xcode.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.dto.client.*;
import cn.xk.xcode.entity.po.MessageChannelAccessClientPo;
import cn.xk.xcode.entity.po.MessageClientChannelPo;
import cn.xk.xcode.entity.vo.client.MessageChannelClientVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.mapper.MessageChannelAccessClientMapper;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.MessageChannelAccessClientService;
import cn.xk.xcode.service.MessageClientChannelService;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.xk.xcode.config.GlobalMessageConstants.*;
import static cn.xk.xcode.entity.def.MessageChannelAccessClientTableDef.MESSAGE_CHANNEL_ACCESS_CLIENT;
import static cn.xk.xcode.entity.def.MessageClientChannelTableDef.MESSAGE_CLIENT_CHANNEL;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@Service
public class MessageChannelAccessClientServiceImpl extends ServiceImpl<MessageChannelAccessClientMapper, MessageChannelAccessClientPo> implements MessageChannelAccessClientService {

    @Resource
    private MessageClientChannelService messageClientChannelService;

    @Resource
    private MessageChannelAccessClientMapper messageChannelAccessClientMapper;

    @Override
    public PageResult<MessageChannelClientVo> queryAll(QueryChannelClientDto queryChannelClientDto) {
        PageUtil.statePage(queryChannelClientDto);
        List<MessageChannelClientVo> messageChannelClientVos = messageChannelAccessClientMapper.queryAllMessageChannelAccessClients(queryChannelClientDto);
        return PageUtil.toPageResult(new PageInfo<>(messageChannelClientVos));
    }

    @Transactional
    @Override
    public Boolean deleteClient(Integer id) {
        messageClientChannelService.remove(MESSAGE_CLIENT_CHANNEL.CLIENT_ID.eq(id));
        return this.removeById(id);
    }

    @Override
    public Boolean updateClient(UpdateChannelClientDto updateChannelClientDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(MESSAGE_CHANNEL_ACCESS_CLIENT.NAME.eq(updateChannelClientDto.getName())
                .and(MESSAGE_CHANNEL_ACCESS_CLIENT.ID.ne(updateChannelClientDto.getId()))))) {
            ExceptionUtil.castServiceException(CLIENT_NAME_ALREADY_EXISTS, updateChannelClientDto.getName());
        }
        if (StrUtil.isBlank(updateChannelClientDto.getEmail()) && StrUtil.isBlank(updateChannelClientDto.getMobile())) {
            ExceptionUtil.castServiceException(MOBILE_AND_EMAIL_BOTH_NULL);
        }
        MessageChannelAccessClientPo channelAccessClientPo = BeanUtil.toBean(updateChannelClientDto, MessageChannelAccessClientPo.class);
        return this.updateById(channelAccessClientPo);
    }

    @Override
    public Boolean registerClient(RegisterChannelClientDto registerChannelClientDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(MESSAGE_CHANNEL_ACCESS_CLIENT.NAME.eq(registerChannelClientDto.getName())))) {
            ExceptionUtil.castServiceException(CLIENT_NAME_ALREADY_EXISTS, registerChannelClientDto.getName());
        }
        if (StrUtil.isBlank(registerChannelClientDto.getEmail()) && StrUtil.isBlank(registerChannelClientDto.getMobile())) {
            ExceptionUtil.castServiceException(MOBILE_AND_EMAIL_BOTH_NULL);
        }
        MessageChannelAccessClientPo channelAccessClientPo = BeanUtil.toBean(registerChannelClientDto, MessageChannelAccessClientPo.class);
        channelAccessClientPo.setRestCount(channelAccessClientPo.getRestCount());
        channelAccessClientPo.setUsedCount(0);
        channelAccessClientPo.setAccessToken(StrUtil.uuid().replace("-", ""));
        return this.save(channelAccessClientPo);
    }

    @Override
    public Boolean addAccessCount(AddChannelClientAccessCount addChannelClientAccessCount) {
        MessageChannelAccessClientPo channelAccessClientPo = this.getById(addChannelClientAccessCount.getId());
        if (ObjectUtil.isNull(channelAccessClientPo)) {
            ExceptionUtil.castServiceException(CLIENT_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(channelAccessClientPo.getStatus())) {
            ExceptionUtil.castServiceException(CLIENT_NOT_ENABLED);
        }
        channelAccessClientPo.setAccessCount(channelAccessClientPo.getAccessCount() + addChannelClientAccessCount.getAccessCount());
        channelAccessClientPo.setRestCount(channelAccessClientPo.getRestCount() + addChannelClientAccessCount.getAccessCount());
        return this.updateById(channelAccessClientPo);
    }

    @Override
    public Boolean refreshToken(ClientRefreshTokenDto clientRefreshTokenDto) {
        MessageChannelAccessClientPo channelAccessClientPo = this.getById(clientRefreshTokenDto.getId());
        if (ObjectUtil.isNull(channelAccessClientPo)) {
            ExceptionUtil.castServiceException(CLIENT_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(channelAccessClientPo.getStatus())) {
            ExceptionUtil.castServiceException(CLIENT_NOT_ENABLED);
        }
        if (!clientRefreshTokenDto.getAccessToken().equals(channelAccessClientPo.getAccessToken())) {
            ExceptionUtil.castServiceException(CLIENT_TOKEN_ERROR);
        }
        channelAccessClientPo.setAccessToken(StrUtil.uuid().replace("-", ""));
        channelAccessClientPo.setTokenRefreshTime(LocalDateTime.now());
        return this.updateById(channelAccessClientPo);
    }

    @Override
    public Boolean unlockClient(Integer id) {
        MessageChannelAccessClientPo channelAccessClientPo = this.getById(id);
        if (ObjectUtil.isNull(channelAccessClientPo)) {
            ExceptionUtil.castServiceException(CLIENT_NOT_EXISTS);
        }
        if (CommonStatusEnum.isEnable(channelAccessClientPo.getStatus())) {
            return true;
        } else {
            return UpdateChain.of(MessageChannelAccessClientPo.class)
                    .set(MESSAGE_CHANNEL_ACCESS_CLIENT.STATUS, CommonStatusEnum.ENABLE.getStatus())
                    .where(MESSAGE_CHANNEL_ACCESS_CLIENT.ID.eq(id))
                    .update();
        }
    }

    @Override
    public Boolean lockClient(Integer id) {
        MessageChannelAccessClientPo channelAccessClientPo = this.getById(id);
        if (ObjectUtil.isNull(channelAccessClientPo)) {
            ExceptionUtil.castServiceException(CLIENT_NOT_EXISTS);
        }
        if (CommonStatusEnum.isDisable(channelAccessClientPo.getStatus())) {
            return true;
        } else {
            return UpdateChain.of(MessageChannelAccessClientPo.class)
                    .set(MESSAGE_CHANNEL_ACCESS_CLIENT.STATUS, CommonStatusEnum.DISABLE.getStatus())
                    .where(MESSAGE_CHANNEL_ACCESS_CLIENT.ID.eq(id))
                    .update();
        }
    }

    @Override
    @Transactional
    public Boolean bindClientChannel(BindClientChannelDto bindClientChannelDto) {
        if (CollectionUtil.isEmpty(bindClientChannelDto.getChannelIdList())) {
            return messageClientChannelService.remove(MESSAGE_CLIENT_CHANNEL.CLIENT_ID.eq(bindClientChannelDto.getId()));
        } else {
            messageClientChannelService.remove(MESSAGE_CLIENT_CHANNEL.CLIENT_ID.eq(bindClientChannelDto.getId()));
            List<MessageClientChannelPo> list = bindClientChannelDto.getChannelIdList().stream().map(
                    o -> {
                        MessageClientChannelPo messageClientChannelPo = new MessageClientChannelPo();
                        messageClientChannelPo.setClientId(bindClientChannelDto.getId());
                        messageClientChannelPo.setChannelId(o);
                        return messageClientChannelPo;
                    }
            ).collect(Collectors.toList());
            return messageClientChannelService.saveBatch(list);
        }
    }

    @Override
    public void validateClient(MessageChannelAccessClientPo messageChannelAccessClientPo, int count) {
        if (ObjectUtil.isNull(messageChannelAccessClientPo)) {
            ExceptionUtil.castServiceException(CLIENT_ACCESS_TOKEN_IS_INVALID);
        }
        LocalDateTime tokenRefreshTime = messageChannelAccessClientPo.getTokenRefreshTime();
        if (tokenRefreshTime.plusSeconds(MESSAGE_ACCESS_CLIENT_TOKEN_EXPIRED_TIME).isBefore(LocalDateTime.now())) {
            ExceptionUtil.castServiceException(CLIENT_ACCESS_TOKEN_IS_EXPIRED);
        }
        if (messageChannelAccessClientPo.getRestCount() <= count) {
            ExceptionUtil.castServiceException(CLIENT_ACCESS_COUNT_IS_NOT_ENOUGH);
        }
        if (!messageClientChannelService.exists(MESSAGE_CLIENT_CHANNEL.CLIENT_ID.eq(messageChannelAccessClientPo.getId()))) {
            ExceptionUtil.castServiceException(CLIENT_NOT_HAS_THIS_CHANNEL_PERMISSION);
        }
    }
}
