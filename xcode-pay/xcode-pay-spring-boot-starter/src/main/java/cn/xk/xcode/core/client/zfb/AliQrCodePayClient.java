package cn.xk.xcode.core.client.zfb;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.xk.xcode.core.config.zfb.AliPayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.enums.PayOrderDisplayModeEnum;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;

import java.util.Objects;

import static cn.xk.xcode.core.config.zfb.AliPayClientConfig.MODE_PUBLIC_KEY_AND_CERTIFICATE;

/**
 * @Author xuk
 * @Date 2024/9/10 15:22
 * @Version 1.0.0
 * @Description AliQrCodePayClient 二维码扫码支付 主扫
 **/
public class AliQrCodePayClient extends AliAbstractPayClient {

    public AliQrCodePayClient(String channel, AliPayClientConfig config) {
        super(channel, config);
    }

    @Override
    public PayOrderResultVo doCreateOrder(PayCreateOrderDto payCreateOrderDto) throws Throwable {
        // 构造请求参数以调用接口
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setBody(payCreateOrderDto.getBody());
        model.setSubject(payCreateOrderDto.getSubject());
        model.setOutTradeNo(payCreateOrderDto.getOutTradeNo());
        model.setTotalAmount(formatAmount(payCreateOrderDto.getPrice()));
        model.setTimeExpire(LocalDateTimeUtil.format(payCreateOrderDto.getExpireTime(), "yyyy-MM-dd HH:mm:ss"));
        model.setProductCode("FACE_TO_FACE_PAYMENT");
        request.setBizModel(model);
        request.setNotifyUrl(payCreateOrderDto.getNotifyUrl());
        request.setReturnUrl(payCreateOrderDto.getReturnUrl());
        // 2.1 执行请求
        AlipayTradePrecreateResponse response;
        if (Objects.equals(config.getMode(), MODE_PUBLIC_KEY_AND_CERTIFICATE)) {
            // 证书模式
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        if (!response.isSuccess()) {
            return buildClosedOrderResultVo(response, payCreateOrderDto.getOutTradeNo());
        }
        return PayOrderResultVo.createWaitingOrderResultVo(PayOrderDisplayModeEnum.QR_CODE.getMode(), response.getQrCode(), payCreateOrderDto.getOutTradeNo(), response);
    }
}
