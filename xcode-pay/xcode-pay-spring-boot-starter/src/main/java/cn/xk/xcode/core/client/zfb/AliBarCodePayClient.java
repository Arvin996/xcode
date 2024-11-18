package cn.xk.xcode.core.client.zfb;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.xk.xcode.core.config.zfb.AliPayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.enums.PayOrderDisplayModeEnum;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alipay.api.domain.AlipayTradePayModel;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradePayResponse;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_FORMATTER;
import static cn.xk.xcode.core.config.zfb.AliPayClientConfig.MODE_PUBLIC_KEY_AND_CERTIFICATE;
import static cn.xk.xcode.enums.PayErrorCodeConstants.AUTH_BAR_CODE_MUST_NOT_NULL;

/**
 * @Author xuk
 * @Date 2024/9/10 14:45
 * @Version 1.0.0
 * @Description AliBarCodePayClient 条形码支付 被扫
 **/
public class AliBarCodePayClient extends AliAbstractPayClient{

    public AliBarCodePayClient(String channel, AliPayClientConfig config) {
        super(channel, config);
    }

    @Override
    public PayOrderResultVo doCreateOrder(PayCreateOrderDto payCreateOrderDto) throws Throwable {
        Map<String, String> channelExtras = payCreateOrderDto.getChannelExtras();
        ObjectUtil.ifNullCastServiceException(channelExtras.getOrDefault("auth_code", null), AUTH_BAR_CODE_MUST_NOT_NULL);
        AlipayTradePayRequest request = new AlipayTradePayRequest();
        AlipayTradePayModel model = new AlipayTradePayModel();
        model.setOutTradeNo(payCreateOrderDto.getOutTradeNo());
        model.setTotalAmount(formatAmount(payCreateOrderDto.getPrice()));
        model.setBody(payCreateOrderDto.getBody());
        model.setSubject(payCreateOrderDto.getSubject());
        model.setScene("bar_code");
        model.setTimeExpire(LocalDateTimeUtil.format(payCreateOrderDto.getExpireTime(), NORM_DATETIME_FORMATTER));
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setAuthCode(channelExtras.get("auth_code"));
        request.setBizModel(model);
        request.setNotifyUrl(payCreateOrderDto.getNotifyUrl());
        request.setReturnUrl(payCreateOrderDto.getReturnUrl());
        // 2.1 执行请求
        AlipayTradePayResponse response;
        if (Objects.equals(config.getMode(), MODE_PUBLIC_KEY_AND_CERTIFICATE)) {
            // 证书模式
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        if (!response.isSuccess()){
            return buildClosedOrderResultVo(response, payCreateOrderDto.getOutTradeNo());
        }
        if ("10000".equals(response.getCode())) { // 免密支付 直接成功
            LocalDateTime successTime = LocalDateTimeUtil.of(response.getGmtPayment());
            return PayOrderResultVo.createSuccessOrderResultVo(response.getTradeNo(), response.getBuyerUserId(), successTime,
                            response.getOutTradeNo(), response);
        }
        // 用户输入密码支付，所以返回 waiting。此时，前端一般会进行轮询
        return PayOrderResultVo.createWaitingOrderResultVo( PayOrderDisplayModeEnum.BAR_CODE.getMode(), "", payCreateOrderDto.getOutTradeNo(), response);
    }
}
