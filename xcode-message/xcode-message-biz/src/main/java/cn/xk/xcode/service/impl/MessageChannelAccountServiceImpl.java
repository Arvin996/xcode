package cn.xk.xcode.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.xk.xcode.entity.dto.account.AddMessageChannelAccountDto;
import cn.xk.xcode.entity.dto.account.UpdateMessageChannelAccountDto;
import cn.xk.xcode.exception.core.ExceptionUtil;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.mapper.MessageChannelAccountMapper;
import cn.xk.xcode.service.MessageChannelAccountService;
import org.springframework.stereotype.Service;

import static cn.xk.xcode.config.GlobalMessageConstants.MESSAGE_CHANNEL_ACCOUNT_NAME_ALREADY_EXISTS;
import static cn.xk.xcode.entity.def.MessageChannelAccountTableDef.MESSAGE_CHANNEL_ACCOUNT_PO;

/**
 * 服务层实现。
 *
 * @author xuk
 * @since 2025-03-10
 */
@Service
public class MessageChannelAccountServiceImpl extends ServiceImpl<MessageChannelAccountMapper, MessageChannelAccountPo> implements MessageChannelAccountService {

    @Override
    public Boolean addMessageChannelAccount(AddMessageChannelAccountDto addMessageChannelAccountDto) {
        validate(addMessageChannelAccountDto);
        MessageChannelAccountPo messageChannelAccountPo = new MessageChannelAccountPo();
        BeanUtil.copyProperties(addMessageChannelAccountDto, messageChannelAccountPo);
        return this.save(messageChannelAccountPo);
    }

    @Override
    public Boolean updateMessageChannelAccount(UpdateMessageChannelAccountDto updateMessageChannelAccountDto) {
        if (this.exists(MESSAGE_CHANNEL_ACCOUNT_PO.ACCOUNT_NAME.eq(updateMessageChannelAccountDto.getAccountName()).and(MESSAGE_CHANNEL_ACCOUNT_PO.ID.ne(updateMessageChannelAccountDto.getId())))) {
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_ACCOUNT_NAME_ALREADY_EXISTS, updateMessageChannelAccountDto.getAccountName());
        }
        MessageChannelAccountPo messageChannelAccountPo = this.getById(updateMessageChannelAccountDto.getId());
        BeanUtil.copyProperties(updateMessageChannelAccountDto, messageChannelAccountPo);
        return this.updateById(messageChannelAccountPo);
    }

    private void validate(AddMessageChannelAccountDto addMessageChannelAccountDto) {
        String accountName = addMessageChannelAccountDto.getAccountName();
        if (this.exists(MESSAGE_CHANNEL_ACCOUNT_PO.ACCOUNT_NAME.eq(accountName))) {
            ExceptionUtil.castServiceException(MESSAGE_CHANNEL_ACCOUNT_NAME_ALREADY_EXISTS, accountName);
        }
    }
}
