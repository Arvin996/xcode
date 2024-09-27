package cn.xk.xcode.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.xk.xcode.convert.order.OrderConvert;
import cn.xk.xcode.core.client.PayClient;
import cn.xk.xcode.core.factory.PayClientFactory;
import cn.xk.xcode.entity.dto.order.PayCreateOrderDto;
import cn.xk.xcode.entity.dto.order.PayOrderSubmitReqDto;
import cn.xk.xcode.entity.po.PayAppPo;
import cn.xk.xcode.entity.po.PayChannelPo;
import cn.xk.xcode.entity.vo.order.PayOrderResultVo;
import cn.xk.xcode.entity.vo.order.PayOrderSubmitRespVO;
import cn.xk.xcode.enums.PayOrderStatusEnums;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.service.PayAppChannelService;
import cn.xk.xcode.service.PayAppService;
import cn.xk.xcode.service.PayChannelService;
import cn.xk.xcode.utils.WebServletUtils;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import cn.xk.xcode.entity.po.PayOrderPo;
import cn.xk.xcode.mapper.PayOrderMapper;
import cn.xk.xcode.service.PayOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.time.LocalDateTime;

import static cn.xk.xcode.entity.def.PayAppChannelTableDef.PAY_APP_CHANNEL_PO;
import static cn.xk.xcode.entity.def.PayChannelTableDef.PAY_CHANNEL_PO;
import static cn.xk.xcode.entity.def.PayOrderTableDef.PAY_ORDER_PO;
import static cn.xk.xcode.enums.PayModuleErrorCodeConstants.*;

/**
 * 服务层实现。
 *
 * @author Administrator
 * @since 2024-09-23
 */
@Service
@Slf4j
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrderPo> implements PayOrderService {

    @Resource
    private PayAppService payAppService;

    @Resource
    private PayClientFactory payClientFactory;

    @Resource
    private PayAppChannelService payAppChannelService;

    @Resource
    private PayChannelService payChannelService;

    @Override
    public PayOrderResultVo getCreateOrder(Long orderId) {
        PayOrderPo payOrderPo = this.getById(orderId);
        return OrderConvert.convert(payOrderPo);
    }

    @Override
    public Long createOrder(PayCreateOrderDto payCreateOrderDto) {
        // 校验app
        PayAppPo payAppPo = payAppService.checkApp(payCreateOrderDto.getAppId());
        // 校验商户订单号是否存在
        PayOrderPo payOrderPo = this.getOne(PAY_ORDER_PO.APP_ID.eq(payCreateOrderDto.getAppId()).and(PAY_ORDER_PO.MERCHANT_ORDER_ID.eq(payCreateOrderDto.getMerchantOrderId())));
        if (ObjectUtil.isNotNull(payOrderPo)) {
            log.warn("[createOrder][appId({}) merchantOrderId({}) 已经存在对应的支付单({})]", payOrderPo.getAppId(),
                    payOrderPo.getMerchantOrderId(), JSONUtil.toJsonStr(payOrderPo)); // 理论来说，不会出现这个情况
            return payOrderPo.getId();
        }
        // 插入数据库
        payOrderPo = OrderConvert.convert(payCreateOrderDto);
        payOrderPo.setNotifyUrl(payAppPo.getOrderNotifyUrl());
        this.save(payOrderPo);
        return payOrderPo.getId();
    }

    @Override
    public PayOrderSubmitRespVO submitOrder(PayOrderSubmitReqDto payOrderSubmitReqDto) {
        PayOrderPo payOrderPo = validateOrderSubmit(payOrderSubmitReqDto.getId());
        PayChannelPo payChannelPo = validateChannelSubmit(payOrderPo.getAppId(), payOrderPo.getChannelCode());
        String ip = WebServletUtils.getClientIP();
        return null;
    }

    private PayOrderPo validateOrderSubmit(Long id) {
        PayOrderPo payOrderPo = this.getById(id);
        if (ObjectUtil.isNull(payOrderPo)) {
            ExceptionUtil.castServiceException(PAY_ORDER_NOT_FOUND);
        }
        if (PayOrderStatusEnums.isSuccess(payOrderPo.getStatus())) {
            ExceptionUtil.castServiceException(PAY_ORDER_ALREADY_PAID);
        }
        if (!PayOrderStatusEnums.PAY_WAITING.getStatus().equals(payOrderPo.getStatus())) {
            ExceptionUtil.castServiceException(PAY_ORDER_STATUS_IS_NOT_WAITING);
        }
        if (LocalDateTime.now().isAfter(payOrderPo.getExpireTime())) {
            ExceptionUtil.castServiceException(ORDER_IS_ALREADY_EXPIRED);
        }
        // 这里还要校验三方服务的状态 防止事因为没有及时回调导致
        String channelCode = payOrderPo.getChannelCode();
        PayClient payClient = payClientFactory.getPayClient(channelCode);
        cn.xk.xcode.entity.order.PayOrderResultVo order = payClient.getOrder(payOrderPo.getOutTradeNo());
        if (ObjectUtil.isNotNull(order) && order.getStatus().equals(PayOrderStatusEnums.PAY_SUCCESS.getStatus())) {
            ExceptionUtil.castServiceException(PAY_ORDER_ALREADY_PAID);
        }
        return payOrderPo;
    }

    private PayChannelPo validateChannelSubmit(Integer appId, String channelCode) {
        PayAppPo payAppPo = payAppService.checkApp(appId);
        PayChannelPo payChannelPo = payChannelService.checkChannel(channelCode);
        if (payAppChannelService.count(PAY_APP_CHANNEL_PO.CHANNEL_ID.eq(payChannelPo.getId()).and(PAY_APP_CHANNEL_PO.APP_ID.eq(payAppPo.getId()))) < 1) {
            ExceptionUtil.castServiceException(PAY_APP_IS_NOT_CONTAINS_CHANNEL, payAppPo.getAppName(), payChannelPo.getChannelCode());
        }
        return payChannelPo;
    }

}
