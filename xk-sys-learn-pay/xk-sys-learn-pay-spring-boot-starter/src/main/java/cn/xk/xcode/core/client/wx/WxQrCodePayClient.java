package cn.xk.xcode.core.client.wx;

import cn.xk.xcode.core.config.wx.WxPayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.enums.PayOrderDisplayModeEnum;
import com.github.binarywang.wxpay.bean.order.WxPayNativeOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.exception.WxPayException;

/**
 * @Author xuk
 * @Date 2024/9/10 22:04
 * @Version 1.0.0
 * @Description WxQrCodePayClient 扫码支付 主扫
 **/
public class WxQrCodePayClient extends AbstractWxPayClient{

    public WxQrCodePayClient(String channel, WxPayClientConfig config) {
        super(channel, config);
    }

    @Override
    protected PayOrderResultVo doCreateOrderV2(PayCreateOrderDto payCreateOrderDto) throws WxPayException {
        WxPayUnifiedOrderRequest request = buildPayUnifiedOrderRequestV2(payCreateOrderDto);
        WxPayNativeOrderResult response = client.createOrder(request);
        return PayOrderResultVo.createWaitingOrderResultVo(
                PayOrderDisplayModeEnum.QR_CODE.getMode(),
                response.getCodeUrl(),
                payCreateOrderDto.getOutTradeNo(),
                response
        );
    }

    @Override
    protected PayOrderResultVo doCreateOrderV3(PayCreateOrderDto payCreateOrderDto) throws WxPayException {
        // 构建 WxPayUnifiedOrderV3Request 对象
        WxPayUnifiedOrderV3Request request = buildPayUnifiedOrderRequestV3(payCreateOrderDto);
        // 执行请求
        WxPayNativeOrderResult response = client.createOrderV3(TradeTypeEnum.NATIVE, request);
        // 转换结果
        return PayOrderResultVo.createWaitingOrderResultVo(PayOrderDisplayModeEnum.QR_CODE.getMode(), response.getCodeUrl(),
                payCreateOrderDto.getOutTradeNo(), response);
    }
}
