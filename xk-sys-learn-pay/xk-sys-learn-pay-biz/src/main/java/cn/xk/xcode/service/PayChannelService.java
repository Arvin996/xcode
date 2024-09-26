package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.channel.AddPayChannelDto;
import cn.xk.xcode.entity.dto.channel.UpdatePayChannelDto;
import cn.xk.xcode.pojo.CommonResult;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.PayChannelPo;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public interface PayChannelService extends IService<PayChannelPo> {

    Boolean addPayChannel(AddPayChannelDto addPayChannelDto);

    Boolean updatePayChannel(UpdatePayChannelDto updatePayChannelDto);
}
