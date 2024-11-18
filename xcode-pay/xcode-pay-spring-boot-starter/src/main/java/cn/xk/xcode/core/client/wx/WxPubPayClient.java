package cn.xk.xcode.core.client.wx;

import cn.hutool.json.JSONUtil;
import cn.xk.xcode.core.config.wx.WxPayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.enums.PayOrderDisplayModeEnum;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.exception.WxPayException;

import java.util.Map;

import static cn.xk.xcode.enums.PayErrorCodeConstants.JSAPI_REQUEST_OPENID_MUST_NOT_NULL;

/**
 * @Author xuk
 * @Date 2024/9/10 22:12
 * @Version 1.0.0
 * @Description WxPubPayClient 微信公众号支付 实际上就就是调用jsapi支付
 **/
public class WxPubPayClient extends AbstractWxPayClient{

    public WxPubPayClient(String channel, WxPayClientConfig config) {
        super(channel, config);
    }

    @Override
    protected PayOrderResultVo doCreateOrderV2(PayCreateOrderDto payCreateOrderDto) throws WxPayException {
        WxPayUnifiedOrderRequest request = buildPayUnifiedOrderRequestV2(payCreateOrderDto).setOpenid(getOpenId(payCreateOrderDto));
        // 执行请求
        WxPayMpOrderResult response = client.createOrder(request);
        return PayOrderResultVo.createWaitingOrderResultVo(PayOrderDisplayModeEnum.APP.getMode(), JSONUtil.toJsonStr(response),
                payCreateOrderDto.getOutTradeNo(), response);
    }

    @Override
    protected PayOrderResultVo doCreateOrderV3(PayCreateOrderDto payCreateOrderDto) throws WxPayException {
        WxPayUnifiedOrderV3Request request = buildPayUnifiedOrderRequestV3(payCreateOrderDto);
        request.setPayer(new WxPayUnifiedOrderV3Request.Payer().setOpenid(getOpenId(payCreateOrderDto)));
        // 执行请求
        WxPayUnifiedOrderV3Result.JsapiResult response = client.createOrderV3(TradeTypeEnum.JSAPI, request);
        // 转换结果
        return PayOrderResultVo.createWaitingOrderResultVo(PayOrderDisplayModeEnum.APP.getMode(), JSONUtil.toJsonStr(response),
                payCreateOrderDto.getOutTradeNo(), response);
    }

    private String getOpenId(PayCreateOrderDto payCreateOrderDto) {
        Map<String, String> channelExtras = payCreateOrderDto.getChannelExtras();
        String openId = channelExtras.getOrDefault("open_id", null);
        ObjectUtil.ifNullCastServiceException(openId, JSAPI_REQUEST_OPENID_MUST_NOT_NULL);
        return openId;
    }
}
