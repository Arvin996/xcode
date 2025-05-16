package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.param.AddMessageChannelParamDto;
import cn.xk.xcode.entity.dto.param.UpdateMessageChannelParamDto;
import cn.xk.xcode.entity.po.MessageChannelParamPo;
import com.mybatisflex.core.service.IService;

/**
 * 服务层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public interface MessageChannelParamService extends IService<MessageChannelParamPo> {

    Boolean addMessageChannelParam(AddMessageChannelParamDto addMessageChannelParamDto);

    Boolean delMessageChannelParam(Integer id);

    Boolean updateMessageChannelParam(UpdateMessageChannelParamDto updateMessageChannelParamDto);
}
