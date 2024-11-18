package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.app.BindAppChannelDto;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.PayAppChannelPo;
import cn.xk.xcode.mapper.PayAppChannelMapper;
import cn.xk.xcode.service.PayAppChannelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.stream.Collectors;

import static cn.xk.xcode.entity.def.PayAppChannelTableDef.PAY_APP_CHANNEL_PO;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-09-23
 */
@Service
public class PayAppChannelServiceImpl extends ServiceImpl<PayAppChannelMapper, PayAppChannelPo> implements PayAppChannelService {

    @Resource
    private PayAppChannelService payAppChannelService;

    @Transactional
    @Override
    public Boolean bindAppChannel(BindAppChannelDto bindAppChannelDto) {
        payAppChannelService.remove(PAY_APP_CHANNEL_PO.APP_ID.eq(bindAppChannelDto.getAppId()));
        if (bindAppChannelDto.getChannelList().isEmpty()) {
            return true;
        }
        payAppChannelService.saveBatch(bindAppChannelDto.getChannelList().stream().map(channel -> PayAppChannelPo.builder().appId(bindAppChannelDto.getAppId()).channelId(channel).build())
                .collect(Collectors.toList()));
        return true;
    }
}
