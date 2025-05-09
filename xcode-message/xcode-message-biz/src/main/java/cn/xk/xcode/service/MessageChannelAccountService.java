package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.account.AddMessageChannelAccountDto;
import cn.xk.xcode.entity.dto.account.UpdateMessageChannelAccountDto;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-03-10
 */
public interface MessageChannelAccountService extends IService<MessageChannelAccountPo> {

    Boolean addMessageChannelAccount(AddMessageChannelAccountDto addMessageChannelAccountDto);

    Boolean updateMessageChannelAccount(UpdateMessageChannelAccountDto updateMessageChannelAccountDto);
}
