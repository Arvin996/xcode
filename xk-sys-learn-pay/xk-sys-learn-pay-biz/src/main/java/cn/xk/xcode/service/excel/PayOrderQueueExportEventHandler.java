package cn.xk.xcode.service.excel;

import cn.hutool.core.util.ObjectUtil;
import cn.xk.xcode.core.queue.event.AbsQueueExportEventHandler;
import cn.xk.xcode.entity.dto.order.ExportOrderDto;
import cn.xk.xcode.entity.po.PayOrderPo;
import cn.xk.xcode.service.PayOrderService;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

import static cn.xk.xcode.entity.def.PayOrderTableDef.PAY_ORDER_PO;

/**
 * @Author xuk
 * @Date 2024/9/29 15:11
 * @Version 1.0.0
 * @Description PayOrderQueueExportEventHandler
 **/
@Component
public class PayOrderQueueExportEventHandler extends AbsQueueExportEventHandler<ExportOrderDto, PayOrderPo> {

    @Resource
    private PayOrderService payOrderService;

    @Override
    public int totalExportCount(ExportOrderDto exportOrderDto) {
        return exportData(exportOrderDto).size();
    }

    @Override
    public List<PayOrderPo> exportData(ExportOrderDto exportOrderDto) {
        QueryWrapper queryWrapper = QueryWrapper
                .create()
                .where("1=1")
                .and(PAY_ORDER_PO.APP_ID.eq(exportOrderDto.getAppId()).when(ObjectUtil.isNotNull(exportOrderDto.getAppId())))
                .and(PAY_ORDER_PO.CHANNEL_CODE.eq(exportOrderDto.getChannelCode()).when(StringUtils.hasText(exportOrderDto.getChannelCode())))
                .and(PAY_ORDER_PO.MERCHANT_ORDER_ID.like(exportOrderDto.getMerchantOrderId()).when(StringUtils.hasText(exportOrderDto.getMerchantOrderId())))
                .and(PAY_ORDER_PO.CHANNEL_ORDER_NO.like(exportOrderDto.getChannelOrderNo()).when(StringUtils.hasText(exportOrderDto.getChannelOrderNo())))
                .and(PAY_ORDER_PO.STATUS.eq(exportOrderDto.getStatus()).when(ObjectUtil.isNotNull(exportOrderDto.getStatus())))
                .and(PAY_ORDER_PO.CREATE_TIME.le(exportOrderDto.getEndTime()).when(ObjectUtil.isNotNull(exportOrderDto.getEndTime())))
                .and(PAY_ORDER_PO.CREATE_TIME.ge(exportOrderDto.getStartTime()).when(ObjectUtil.isNotNull(exportOrderDto.getStartTime())));
        return payOrderService.list(queryWrapper);
    }
}
