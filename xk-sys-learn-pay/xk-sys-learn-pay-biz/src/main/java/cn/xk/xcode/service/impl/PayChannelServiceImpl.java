package cn.xk.xcode.service.impl;

import cn.xk.xcode.entity.dto.channel.AddPayChannelDto;
import cn.xk.xcode.entity.dto.channel.UpdatePayChannelDto;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.pojo.CommonResult;
import cn.xk.xcode.utils.object.BeanUtil;
import com.mybatisflex.core.update.UpdateChain;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.PayChannelPo;
import cn.xk.xcode.mapper.PayChannelMapper;
import cn.xk.xcode.service.PayChannelService;
import org.springframework.stereotype.Service;

import static cn.xk.xcode.entity.def.PayChannelTableDef.PAY_CHANNEL_PO;
import static cn.xk.xcode.enums.PayModuleErrorCodeConstants.CHANNEL_CODE_ALREADY_EXIST;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-09-23
 */
@Service
public class PayChannelServiceImpl extends ServiceImpl<PayChannelMapper, PayChannelPo> implements PayChannelService {

    @Override
    public Boolean addPayChannel(AddPayChannelDto addPayChannelDto) {
        if (this.count(PAY_CHANNEL_PO.CHANNEL_CODE.eq(addPayChannelDto.getChannelCode())) > 0) {
            ExceptionUtil.castServiceException(CHANNEL_CODE_ALREADY_EXIST, addPayChannelDto.getChannelCode());
        }
        return this.save(BeanUtil.toBean(addPayChannelDto, PayChannelPo.class));
    }

    @Override
    public Boolean updatePayChannel(UpdatePayChannelDto updatePayChannelDto) {
        if (this.count(PAY_CHANNEL_PO.CHANNEL_CODE.eq(updatePayChannelDto.getChannelCode()).and(PAY_CHANNEL_PO.ID.ne(updatePayChannelDto.getId()))) > 0) {
            ExceptionUtil.castServiceException(CHANNEL_CODE_ALREADY_EXIST, updatePayChannelDto.getChannelCode());
        }
        return UpdateChain
                .of(PayChannelPo.class)
                .set(PAY_CHANNEL_PO.CHANNEL_CODE, updatePayChannelDto.getChannelCode())
                .set(PAY_CHANNEL_PO.FEE_RATE, updatePayChannelDto.getFeeRate())
                .set(PAY_CHANNEL_PO.REMARK, updatePayChannelDto.getRemark())
                .set(PAY_CHANNEL_PO.STATUS, updatePayChannelDto.getStatus())
                .where(PAY_CHANNEL_PO.ID.eq(updatePayChannelDto.getId()))
                .update();
    }

}
