package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.channel.AddMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.DelMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.QueryMessageChannelDto;
import cn.xk.xcode.entity.dto.channel.UpdateMessageChannelDto;
import cn.xk.xcode.entity.vo.MessageChannelVo;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.MessageChannelPo;

import java.util.List;

/**
 *  服务层。
 *
 * @author xuk
 * @since 2025-03-10
 */
public interface MessageChannelService extends IService<MessageChannelPo> {

    Boolean addMessageChannel(AddMessageChannelDto addMessageChannelDto);

    Boolean deleteChannel(DelMessageChannelDto delMessageChannelDto);

    Boolean updateMessageChannel(UpdateMessageChannelDto updateMessageChannelDto);

    List<MessageChannelVo> getMessageChannel(QueryMessageChannelDto queryMessageChannelDto);
}
