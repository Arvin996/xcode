package cn.xk.xcode.core.client.wx;

import cn.xk.xcode.core.config.wx.WxPayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.enums.PayOrderDisplayModeEnum;
import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.exception.WxPayException;

/**
 * @Author xuk
 * @Date 2024/9/10 21:24
 * @Version 1.0.0
 * @Description WxAppPayClient 微信app支付
 **/
public class WxAppPayClient extends AbstractWxPayClient{

    public WxAppPayClient(String channel, WxPayClientConfig config) {
        super(channel, config);
    }

    @Override
    protected PayOrderResultVo doCreateOrderV2(PayCreateOrderDto payCreateOrderDto) throws WxPayException {
        WxPayUnifiedOrderRequest request = buildPayUnifiedOrderRequestV2(payCreateOrderDto);
        WxPayMpOrderResult response = client.createOrder(request);
        return PayOrderResultVo.createWaitingOrderResultVo(PayOrderDisplayModeEnum.APP.getMode(), JSON.toJSONString(response),
                payCreateOrderDto.getOutTradeNo(), response);
    }

    @Override
    protected PayOrderResultVo doCreateOrderV3(PayCreateOrderDto payCreateOrderDto) throws WxPayException {
        WxPayUnifiedOrderV3Request request = buildPayUnifiedOrderRequestV3(payCreateOrderDto);
        WxPayUnifiedOrderV3Result.AppResult response = client.createOrderV3(TradeTypeEnum.APP, request);
        return PayOrderResultVo.createWaitingOrderResultVo(PayOrderDisplayModeEnum.APP.getMode(), JSON.toJSONString(response),
                payCreateOrderDto.getOutTradeNo(), response);
    }
}
