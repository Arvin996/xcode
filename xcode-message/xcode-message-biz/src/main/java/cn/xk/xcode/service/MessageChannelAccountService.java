package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.account.AddMessageChannelAccountDto;
import cn.xk.xcode.entity.dto.account.QueryMessageChannelAccountDto;
import cn.xk.xcode.entity.dto.account.UpdateMessageChannelAccountDto;
import cn.xk.xcode.entity.po.MessageChannelAccountPo;
import cn.xk.xcode.entity.vo.account.MessageChannelAccountVo;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public interface MessageChannelAccountService extends IService<MessageChannelAccountPo> {

    Boolean addMessageChannelAccount(AddMessageChannelAccountDto addMessageChannelAccountDto);

    Boolean delMessageChannelAccount(Integer id);

    Boolean updateMessageChannelAccount(UpdateMessageChannelAccountDto updateMessageChannelAccountDto);

    PageResult<MessageChannelAccountVo> queryAll(QueryMessageChannelAccountDto queryMessageChannelAccountDto);
}
