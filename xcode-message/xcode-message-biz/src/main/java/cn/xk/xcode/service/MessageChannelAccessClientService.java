package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.client.*;
import cn.xk.xcode.entity.po.MessageChannelAccessClientPo;
import cn.xk.xcode.entity.vo.client.MessageChannelClientVo;
import cn.xk.xcode.pojo.PageResult;
import com.mybatisflex.core.service.IService;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public interface MessageChannelAccessClientService extends IService<MessageChannelAccessClientPo> {

    PageResult<MessageChannelClientVo> queryAll(QueryChannelClientDto queryChannelClientDto);

    Boolean deleteClient(Integer id);

    Boolean updateClient(UpdateChannelClientDto updateChannelClientDto);

    Boolean registerClient(RegisterChannelClientDto registerChannelClientDto);

    Boolean addAccessCount(AddChannelClientAccessCount addChannelClientAccessCount);

    Boolean refreshToken(ClientRefreshTokenDto clientRefreshTokenDto);

    Boolean unlockClient(Integer id);

    Boolean lockClient(Integer id);

    Boolean bindClientChannel(BindClientChannelDto bindClientChannelDto);

    void validateClient(MessageChannelAccessClientPo messageChannelAccessClientPo, int count);
}
