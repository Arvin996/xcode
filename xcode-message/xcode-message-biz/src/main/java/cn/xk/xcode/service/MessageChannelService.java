package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.channel.AddMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.QueryMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.UpdateMessageChannelDto;
import cn.xk.xcode.entity.po.MessageChannelPo;
import cn.xk.xcode.entity.vo.channel.MessageChannelVo;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public interface MessageChannelService extends IService<MessageChannelPo> {

    Boolean addMessageChannel(AddMessageChannelDto addMessageChannelDto);

    Boolean deleteChannel(Integer id);

    Boolean updateMessageChannel(UpdateMessageChannelDto updateMessageChannelDto);

    PageResult<MessageChannelVo> getMessageChannel(QueryMessageChannelDto queryMessageChannelDto);
}
