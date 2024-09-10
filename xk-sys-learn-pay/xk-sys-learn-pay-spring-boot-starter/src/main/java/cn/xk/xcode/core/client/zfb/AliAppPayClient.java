package cn.xk.xcode.core.client.zfb;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.xk.xcode.core.config.zfb.AliPayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.enums.PayOrderDisplayModeEnum;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_FORMATTER;

/**
 * @Author xuk
 * @Date 2024/9/10 14:29
 * @Version 1.0.0
 * @Description AliAppPayClient 支付宝app支付
 **/
public class AliAppPayClient extends AliAbstractPayClient {

    public AliAppPayClient(String channel, AliPayClientConfig config) {
        super(channel, config);
    }

    @Override
    public PayOrderResultVo doCreateOrder(PayCreateOrderDto payCreateOrderDto) throws Throwable {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setOutTradeNo(payCreateOrderDto.getOutTradeNo());
        model.setBody(payCreateOrderDto.getBody());
        model.setSubject(payCreateOrderDto.getSubject());
        model.setTotalAmount(formatAmount(payCreateOrderDto.getPrice()));
        model.setTimeExpire(LocalDateTimeUtil.format(payCreateOrderDto.getExpireTime(), NORM_DATETIME_FORMATTER));
        model.setProductCode("QUICK_MSECURITY_PAY");
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(payCreateOrderDto.getNotifyUrl());
        request.setReturnUrl(payCreateOrderDto.getReturnUrl());
        String displayMode = PayOrderDisplayModeEnum.APP.getMode();
        AlipayTradeAppPayResponse response = client.sdkExecute(request);
        if (!response.isSuccess()) {
            return buildClosedOrderResultVo(response, payCreateOrderDto.getOutTradeNo());
        }
        return PayOrderResultVo.createWaitingOrderResultVo(displayMode, response.getBody(), payCreateOrderDto.getOutTradeNo(), response);
    }
}
