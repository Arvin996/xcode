package cn.xk.xcode.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.CommonStatusEnum;
import cn.xk.xcode.entity.dto.account.AddMessageChannelAccountDto;
import cn.xk.xcode.entity.dto.account.QueryMessageChannelAccountDto;
import cn.xk.xcode.entity.dto.account.UpdateMessageChannelAccountDto;
import cn.xk.xcode.entity.po.MessageChannelAccountParamValuePo;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.po.MessageChannelParamPo;
import cn.xk.xcode.entity.po.MessageChannelPo;
import cn.xk.xcode.entity.vo.account.MessageChannelAccountVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.mapper.MessageChannelAccountMapper;
import cn.xk.xcode.pojo.PageResult;
import cn.xk.xcode.service.MessageChannelAccountParamValueService;
import cn.xk.xcode.service.MessageChannelAccountService;
import cn.xk.xcode.service.MessageChannelParamService;
import cn.xk.xcode.service.MessageChannelService;
import cn.xk.xcode.utils.PageUtil;
import cn.xk.xcode.utils.collections.CollectionUtil;
import cn.xk.xcode.utils.object.BeanUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatisflex.core.logicdelete.LogicDeleteManager;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.xk.xcode.config.GlobalMessageConstants.*;
import static cn.xk.xcode.entity.def.MessageChannelAccountParamValueTableDef.MESSAGE_CHANNEL_ACCOUNT_PARAM_VALUE;
import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT;
import static cn.xk.xcode.entity.def.MessageChannelParamTableDef.MESSAGE_CHANNEL_PARAM;
import static cn.xk.xcode.entity.def.MessageChannelTableDef.MESSAGE_CHANNEL;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2025-05-15
 */
@Service
public class MessageChannelAccountServiceImpl extends ServiceImpl<MessageChannelAccountMapper, MessageChannelAccountPo> implements MessageChannelAccountService {

    @Resource
    private MessageChannelParamService messageChannelParamService;

    @Resource
    private MessageChannelAccountParamValueService messageChannelAccountParamValueService;

    @Resource
    private MessageChannelService messageChannelService;

    @Resource
    private MessageChannelAccountMapper messageChannelAccountMapper;

    @Transactional
    @Override
    public Boolean addMessageChannelAccount(AddMessageChannelAccountDto addMessageChannelAccountDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(MESSAGE_CHANNEL_ACCOUNT.ACCOUNT_NAME.eq(addMessageChannelAccountDto.getAccountName())
                .and(MESSAGE_CHANNEL_ACCOUNT.CHANNEL_ID.eq(addMessageChannelAccountDto.getChannelId()))))) {
            ExceptionUtil.castServiceException(CHANNEL_ACCOUNT_NAME_ALREADY_EXISTS, addMessageChannelAccountDto.getAccountName());
        }
        MessageChannelPo messageChannelPo = messageChannelService.getById(addMessageChannelAccountDto.getChannelId());
        if (ObjectUtil.isNull(messageChannelPo)) {
            ExceptionUtil.castServiceException(CHANNEL_NOT_EXISTS);
        }
        MessageChannelAccountPo messageChannelAccountPo = BeanUtil.toBean(addMessageChannelAccountDto, MessageChannelAccountPo.class);
        this.save(messageChannelAccountPo);
        List<MessageChannelParamPo> messageChannelParamPoList = messageChannelParamService.list(MESSAGE_CHANNEL_PARAM.CHANNEL_ID.eq(messageChannelPo.getId()));
        Map<Integer, String> channelParamValueMap = addMessageChannelAccountDto.getChannelParamValueMap();
        List<MessageChannelAccountParamValuePo> list = new ArrayList<>();
        for (MessageChannelParamPo messageChannelParamPo : messageChannelParamPoList) {
            if (!channelParamValueMap.containsKey(messageChannelParamPo.getId())) {
                ExceptionUtil.castServiceException(MESSAGE_CHANNEL_PARAM_MISSING, messageChannelPo.getName(), messageChannelParamPo.getName());
            }
            if (StrUtil.isBlank(channelParamValueMap.get(messageChannelParamPo.getId()))) {
                if (CommonStatusEnum.isEnable(messageChannelParamPo.getRequired())) {
                    ExceptionUtil.castServiceException(MESSAGE_CHANNEL_PARAM_IS_REQUIRED, messageChannelPo.getName(), messageChannelParamPo.getName());
                }
            } else {
                MessageChannelAccountParamValuePo messageChannelAccountParamValuePo = new MessageChannelAccountParamValuePo();
                messageChannelAccountParamValuePo.setAccountId(messageChannelAccountPo.getId());
                messageChannelAccountParamValuePo.setChannelParamId(messageChannelParamPo.getId());
                messageChannelAccountParamValuePo.setParamValue(channelParamValueMap.get(messageChannelParamPo.getId()));
                list.add(messageChannelAccountParamValuePo);
            }
        }
        if (CollectionUtil.isNotEmpty(list)) {
            return messageChannelAccountParamValueService.saveBatch(list);
        }
        return true;
    }

    @Transactional
    @Override
    public Boolean delMessageChannelAccount(Integer id) {
        this.removeById(id);
        return messageChannelAccountParamValueService.remove(MESSAGE_CHANNEL_ACCOUNT_PARAM_VALUE.ACCOUNT_ID.eq(id));
    }

    @Transactional
    @Override
    @SuppressWarnings("all")
    public Boolean updateMessageChannelAccount(UpdateMessageChannelAccountDto updateMessageChannelAccountDto) {
        if (LogicDeleteManager.execWithoutLogicDelete(() -> this.exists(MESSAGE_CHANNEL_ACCOUNT.ACCOUNT_NAME.eq(updateMessageChannelAccountDto.getAccountName())
                .and(MESSAGE_CHANNEL_ACCOUNT.CHANNEL_ID.eq(updateMessageChannelAccountDto.getChannelId()))
                .and(MESSAGE_CHANNEL_ACCOUNT.ID.ne(updateMessageChannelAccountDto.getId()))))) {
            ExceptionUtil.castServiceException(CHANNEL_ACCOUNT_NAME_ALREADY_EXISTS, updateMessageChannelAccountDto.getAccountName());
        }
        MessageChannelPo messageChannelPo = messageChannelService.getById(updateMessageChannelAccountDto.getChannelId());
        if (ObjectUtil.isNull(messageChannelPo)) {
            ExceptionUtil.castServiceException(CHANNEL_NOT_EXISTS);
        }
        MessageChannelAccountPo messageChannelAccountPo = BeanUtil.toBean(updateMessageChannelAccountDto, MessageChannelAccountPo.class);
        this.updateById(messageChannelAccountPo);
        List<MessageChannelParamPo> messageChannelParamPoList = messageChannelParamService.list(MESSAGE_CHANNEL_PARAM.CHANNEL_ID.eq(messageChannelPo.getId()));
        Map<Integer, String> channelParamValueMap = updateMessageChannelAccountDto.getChannelParamValueMap();
        List<MessageChannelAccountParamValuePo> list = new ArrayList<>();
        for (MessageChannelParamPo messageChannelParamPo : messageChannelParamPoList) {
            if (!channelParamValueMap.containsKey(messageChannelParamPo.getId())) {
                ExceptionUtil.castServiceException(MESSAGE_CHANNEL_PARAM_MISSING, messageChannelPo.getName(), messageChannelParamPo.getName());
            }
            if (StrUtil.isBlank(channelParamValueMap.get(messageChannelParamPo.getId()))) {
                if (CommonStatusEnum.isEnable(messageChannelParamPo.getRequired())) {
                    ExceptionUtil.castServiceException(MESSAGE_CHANNEL_PARAM_IS_REQUIRED, messageChannelPo.getName(), messageChannelParamPo.getName());
                }
            } else {
                MessageChannelAccountParamValuePo messageChannelAccountParamValuePo = new MessageChannelAccountParamValuePo();
                messageChannelAccountParamValuePo.setAccountId(messageChannelAccountPo.getId());
                messageChannelAccountParamValuePo.setChannelParamId(messageChannelParamPo.getId());
                messageChannelAccountParamValuePo.setParamValue(channelParamValueMap.get(messageChannelParamPo.getId()));
                list.add(messageChannelAccountParamValuePo);
            }
        }
        messageChannelAccountParamValueService.remove(MESSAGE_CHANNEL_ACCOUNT_PARAM_VALUE.ACCOUNT_ID.eq(messageChannelAccountPo.getId())
                .and(MESSAGE_CHANNEL_ACCOUNT_PARAM_VALUE.CHANNEL_PARAM_ID.in(channelParamValueMap.keySet())));
        return messageChannelAccountParamValueService.saveBatch(list);
    }

    @Override
    public PageResult<MessageChannelAccountVo> queryAll(QueryMessageChannelAccountDto queryMessageChannelAccountDto) {
        PageHelper.startPage(Math.toIntExact(queryMessageChannelAccountDto.getPageNo()), Math.toIntExact(queryMessageChannelAccountDto.getPageSize()));
        List<MessageChannelAccountVo> messageChannelAccountVos = messageChannelAccountMapper.queryAllMessageChannelAccounts(queryMessageChannelAccountDto);
        return PageUtil.toPageResult(new PageInfo<>(messageChannelAccountVos));
    }
}
