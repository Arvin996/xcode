package cn.xk.xcode.core.client.zfb;

import cn.hutool.http.Method;
import cn.xk.xcode.core.config.zfb.AliPayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.enums.PayOrderDisplayModeEnum;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;

/**
 * @Author xuk
 * @Date 2024/9/10 15:30
 * @Version 1.0.0
 * @Description AliWapPayClient 手机网站支付
 **/
public class AliWapPayClient extends AliAbstractPayClient {
    public AliWapPayClient(String channel, AliPayClientConfig config) {
        super(channel, config);
    }

    @Override
    public PayOrderResultVo doCreateOrder(PayCreateOrderDto payCreateOrderDto) throws Throwable {
        // 1.1 构建 AlipayTradeWapPayModel 请求
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        // ① 通用的参数
        model.setOutTradeNo(payCreateOrderDto.getOutTradeNo());
        model.setSubject(payCreateOrderDto.getSubject());
        model.setBody(payCreateOrderDto.getBody());
        model.setTotalAmount(formatAmount(payCreateOrderDto.getPrice()));
        model.setProductCode("QUICK_WAP_PAY"); // 销售产品码. 目前 Wap 支付场景下仅支持 QUICK_WAP_PAY

        // ③ 支付宝 Wap 支付只有一种展示：URL
        String displayMode = PayOrderDisplayModeEnum.URL.getMode();

        // 1.2 构建 AlipayTradeWapPayRequest 请求
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(payCreateOrderDto.getNotifyUrl());
        request.setReturnUrl(payCreateOrderDto.getReturnUrl());
        model.setQuitUrl(payCreateOrderDto.getReturnUrl());

        // 2.1 执行请求
        AlipayTradeWapPayResponse response = client.pageExecute(request, Method.GET.name());
        // 2.2 处理结果
        if (!response.isSuccess()) {
            return buildClosedOrderResultVo(response, payCreateOrderDto.getOutTradeNo());
        }
        return PayOrderResultVo.createWaitingOrderResultVo(displayMode, response.getBody(),
                payCreateOrderDto.getOutTradeNo(), response);
    }
}
