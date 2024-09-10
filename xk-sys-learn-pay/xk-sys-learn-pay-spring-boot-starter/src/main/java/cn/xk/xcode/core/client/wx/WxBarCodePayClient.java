package cn.xk.xcode.core.client.wx;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.config.wx.WxPayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.enums.PayOrderDisplayModeEnum;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.request.WxPayMicropayRequest;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.xk.xcode.enums.PayErrorCodeConstants.AUTH_BAR_CODE_MUST_NOT_NULL;

/**
 * @Author xuk
 * @Date 2024/9/10 21:40
 * @Version 1.0.0
 * @Description WxBrCodePayClient 付款吗支付 被扫
 **/
@Slf4j
public class WxBarCodePayClient extends AbstractWxPayClient {

    public WxBarCodePayClient(String channel, WxPayClientConfig config) {
        super(channel, config);
    }

    @Override
    protected PayOrderResultVo doCreateOrderV2(PayCreateOrderDto payCreateOrderDto) throws WxPayException {
        WxPayMicropayRequest request = WxPayMicropayRequest.newBuilder()
                .outTradeNo(payCreateOrderDto.getOutTradeNo())
                .body(payCreateOrderDto.getSubject())
                .detail(payCreateOrderDto.getBody())
                .totalFee(payCreateOrderDto.getPrice()) // 单位分
                .timeExpire(formatDateV2(payCreateOrderDto.getExpireTime()))
                .spbillCreateIp(payCreateOrderDto.getUserIp())
                .authCode(getAuthCode(payCreateOrderDto))
                .build();
        // 轮询调用支付
        //提交支付请求后微信会同步返回支付结果。当返回结果为“系统错误”时，商户系统等待5秒后调用【查询订单API】，查询支付实际交易结果；
        // 当返回结果为“USERPAYING”时，商户系统可设置间隔时间(建议10秒)重新查询支付结果，直到支付成功或超时(建议45秒)；
        // 执行请求，重试直到失败（过期），或者成功
        WxPayException lastWxPayException = null;
        for (int i = 1; i < Byte.MAX_VALUE; i++) {
            try {
                WxPayMicropayResult response = client.micropay(request);
                // 支付成功，例如说：1）用户输入了密码；2）用户免密支付
                PayOrderResultVo successOrderResultVo = PayOrderResultVo.createSuccessOrderResultVo(
                        response.getTransactionId(),
                        response.getOpenid(),
                        parseDateV2(response.getTimeEnd()),
                        payCreateOrderDto.getOutTradeNo(),
                        response
                );
                successOrderResultVo.setDisplayMode(PayOrderDisplayModeEnum.BAR_CODE.getMode());
                return successOrderResultVo;
            } catch (WxPayException ex) {
                lastWxPayException = ex;
                // 如果不满足这 3 种任一的，则直接抛出 WxPayException 异常，不仅需处理
                // 1. SYSTEMERROR：接口返回错误：请立即调用被扫订单结果查询API，查询当前订单状态，并根据订单的状态决定下一步的操作。
                // 2. USERPAYING：用户支付中，需要输入密码：等待 5 秒，然后调用被扫订单结果查询 API，查询当前订单的不同状态，决定下一步的操作。
                // 3. BANKERROR：银行系统异常：请立即调用被扫订单结果查询 API，查询当前订单的不同状态，决定下一步的操作。
                if (!StrUtil.equalsAny(ex.getErrCode(), "SYSTEMERROR", "USERPAYING", "BANKERROR")) {
                    throw ex;
                }
                // 等待 5 秒，继续下一轮重新发起支付
                log.info("[doUnifiedOrderV2][发起微信 Bar 支付第({})失败，等待下一轮重试，请求({})，响应({})]", i,
                        JSON.toJSONString(request), ex.getMessage());
                ThreadUtil.sleep(5, TimeUnit.SECONDS);
            }
        }
        throw lastWxPayException;
    }

    private String getAuthCode(PayCreateOrderDto payCreateOrderDto) {
        Map<String, String> channelExtras = payCreateOrderDto.getChannelExtras();
        String authCode = channelExtras.getOrDefault("auth_code", null);
        ObjectUtil.ifNullCastServiceException(authCode, AUTH_BAR_CODE_MUST_NOT_NULL);
        return authCode;
    }

    @Override
    protected PayOrderResultVo doCreateOrderV3(PayCreateOrderDto payCreateOrderDto) throws WxPayException {
        return doCreateOrderV2(payCreateOrderDto);
    }
}
