package cn.xk.xcode.core.client.wx;

import cn.xk.xcode.core.config.wx.WxPayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.enums.PayOrderDisplayModeEnum;
import com.github.binarywang.wxpay.bean.order.WxPayMwebOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.exception.WxPayException;

/**
 * @Author xuk
 * @Date 2024/9/10 22:24
 * @Version 1.0.0
 * @Description WxH5PayClient 微信H5支付 MWEB
 **/
public class WxH5PayClient extends AbstractWxPayClient{
    public WxH5PayClient(String channel, WxPayClientConfig config) {
        super(channel, config);
    }

    @Override
    protected PayOrderResultVo doCreateOrderV2(PayCreateOrderDto payCreateOrderDto) throws WxPayException {
        WxPayUnifiedOrderRequest request = buildPayUnifiedOrderRequestV2(payCreateOrderDto);
        WxPayMwebOrderResult response = client.createOrder(request);
        return PayOrderResultVo.createWaitingOrderResultVo(
                PayOrderDisplayModeEnum.URL.getMode(),
                response.getMwebUrl(),
                payCreateOrderDto.getOutTradeNo(),
                response
        );
    }

    @Override
    protected PayOrderResultVo doCreateOrderV3(PayCreateOrderDto payCreateOrderDto) throws WxPayException {
        WxPayUnifiedOrderV3Request request = buildPayUnifiedOrderRequestV3(payCreateOrderDto);
        WxPayMwebOrderResult response = client.createOrderV3(TradeTypeEnum.H5,  request);
        return PayOrderResultVo.createWaitingOrderResultVo(
                PayOrderDisplayModeEnum.URL.getMode(),
                response.getMwebUrl(),
                payCreateOrderDto.getOutTradeNo(),
                response
        );
    }
}
