package cn.xk.xcode.core.client.zfb;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.Method;
import cn.xk.xcode.core.config.zfb.AliPayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.enums.PayOrderDisplayModeEnum;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;

import java.util.Objects;

/**
 * @Author xuk
 * @Date 2024/9/10 15:09
 * @Version 1.0.0
 * @Description AliPcPayClient pc支付 跳转到支付宝支付
 **/
public class AliPcPayClient extends AliAbstractPayClient {

    public AliPcPayClient(String channel, AliPayClientConfig config) {
        super(channel, config);
    }

    @Override
    public PayOrderResultVo doCreateOrder(PayCreateOrderDto payCreateOrderDto) throws Throwable {
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(payCreateOrderDto.getOutTradeNo());
        model.setSubject(payCreateOrderDto.getSubject());
        model.setTotalAmount(formatAmount(payCreateOrderDto.getPrice()));
        model.setBody(payCreateOrderDto.getBody());
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        model.setTimeExpire(LocalDateTimeUtil.format(payCreateOrderDto.getExpireTime(), "yyyy-MM-dd HH:mm:ss"));
        model.setQrPayMode("2");
        String displayMode = ObjectUtil.defaultIfNull(payCreateOrderDto.getDisplayMode(),
                PayOrderDisplayModeEnum.URL.getMode());
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(payCreateOrderDto.getNotifyUrl());
        request.setReturnUrl(payCreateOrderDto.getReturnUrl());
        // 2.1 执行请求
        AlipayTradePagePayResponse response;
        if (Objects.equals(displayMode, PayOrderDisplayModeEnum.FORM.getMode())) {
            response = client.pageExecute(request, Method.POST.name()); // 需要特殊使用 POST 请求
        } else {
            response = client.pageExecute(request, Method.GET.name());
        }
        if (!response.isSuccess()) {
            buildClosedOrderResultVo(response, payCreateOrderDto.getOutTradeNo());
        }
        return PayOrderResultVo.createWaitingOrderResultVo(displayMode, response.getBody(), payCreateOrderDto.getOutTradeNo(), response);
    }
}
