package cn.xk.xcode.service;

import cn.xk.xcode.entity.dto.app.BindAppChannelDto;
import com.mybatisflex.core.service.IService;
import cn.xk.xcode.entity.po.PayAppChannelPo;

/**
 *  服务层。
 *
 * @author Administrator
 * @since 2024-09-23
 */
public interface PayAppChannelService extends IService<PayAppChannelPo> {

    Boolean bindAppChannel(BindAppChannelDto bindAppChannelDto);
}
