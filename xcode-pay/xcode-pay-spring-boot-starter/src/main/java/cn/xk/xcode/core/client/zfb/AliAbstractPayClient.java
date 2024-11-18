package cn.xk.xcode.core.client.zfb;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.xk.xcode.core.client.AbstractPayClient;
import cn.xk.xcode.core.config.zfb.AliPayClientConfig;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.entity.refund.PayCreateRefundDto;
import cn.xk.xcode.entity.refund.PayRefundResultVo;
import cn.xk.xcode.enums.PayOrderStatusEnums;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alipay.api.AlipayConfig;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

import static cn.hutool.core.date.DatePattern.NORM_DATETIME_FORMATTER;
import static cn.xk.xcode.core.config.zfb.AliPayClientConfig.MODE_PUBLIC_KEY_AND_CERTIFICATE;
import static cn.xk.xcode.enums.PayErrorCodeConstants.PAY_ORDER_RESPONSE_BODY_IS_NOT_CORRECT;
import static cn.xk.xcode.enums.PayOrderStatusEnums.*;

/**
 * @Author xuk
 * @Date 2024/9/9 9:04
 * @Version 1.0.0
 * @Description AliAbstractPayClient
 **/
public abstract class AliAbstractPayClient extends AbstractPayClient<AliPayClientConfig> {

    protected DefaultAlipayClient client;

    public AliAbstractPayClient(String channel, AliPayClientConfig config) {
        super(channel, config);
    }

    @Override
    @SneakyThrows
    public void doInit() {
        // 初始化client
        AlipayConfig alipayConfig = new AlipayConfig();
        BeanUtil.copyProperties(config, alipayConfig, false);
        client = new DefaultAlipayClient(alipayConfig);
    }

    @Override
    public PayOrderResultVo doGetOrder(String outTradeNo) throws Throwable {
        // 构造请求参数以调用接口
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);
        request.setBizModel(model);
        AlipayTradeQueryResponse response;
        if (Objects.equals(config.getMode(), MODE_PUBLIC_KEY_AND_CERTIFICATE)) {
            // 证书模式
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        // 说明订单不存在
        if (!response.isSuccess()) {
            return PayOrderResultVo.createClosedOrderResultVo(
                    response.getSubCode(), response.getSubMsg(),
                    outTradeNo, response.getMsg()
            );
        }
        //trade_status必选string(32)
        //【描述】交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、
        // TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、
        // TRADE_SUCCESS（交易支付成功）、
        // TRADE_FINISHED（交易结束，不可退款）
        String tradeStatus = response.getTradeStatus();
        Integer status = parseOrderStatus(tradeStatus);
        ObjectUtil.ifNullCastServiceException(status, PAY_ORDER_RESPONSE_BODY_IS_NOT_CORRECT, response);
        // 根据状态返回对应的实体类
        return PayOrderResultVo.createAnyStatusOrderResultVo(status, response.getTradeNo(), response.getBuyerUserId(), LocalDateTimeUtil.of(response.getSendPayDate()),
                outTradeNo, response);
    }

    private Integer parseOrderStatus(String status) {
        return Objects.equals("WAIT_BUYER_PAY", status) ? PAY_WAITING.getStatus()
                : ObjectUtil.equalsAny("TRADE_SUCCESS", "TRADE_FINISHED") ? PAY_SUCCESS.getStatus()
                : Objects.equals("TRADE_CLOSED", status) ? PAY_CLOSED.getStatus() : null;

    }

    @Override
    public PayRefundResultVo doCreateRefund(PayCreateRefundDto payCreateRefundDto) throws Throwable {
        //
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(payCreateRefundDto.getOutTradeNo());
        model.setOutRequestNo(payCreateRefundDto.getOutRefundNo());
        model.setRefundAmount(formatAmount(payCreateRefundDto.getRefundPrice()));
        model.setRefundReason(payCreateRefundDto.getReason());
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);
        // 2.1 执行请求
        AlipayTradeRefundResponse response;
        if (Objects.equals(config.getMode(), MODE_PUBLIC_KEY_AND_CERTIFICATE)) {  // 证书模式
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        if (!response.isSuccess()) {
            // 业务系统异常 退费可能成功 也可能失败 返回 WAIT 状态. 后续 job 会轮询
            if ("ACQ.SYSTEM_ERROR".equals(response.getSubCode())) {
                return PayRefundResultVo.createWaitingOfRefundResultVo(null, payCreateRefundDto.getOutRefundNo(), response);
            }
            return PayRefundResultVo.createFailureOfRefundResultVo(response.getSubCode(), response.getSubMsg(), payCreateRefundDto.getOutRefundNo(), response);
        }
        // 2.2 创建返回结果
        // 支付宝只要退款调用返回 success，就认为退款成功，不需要回调。具体可见 parseNotify 方法的说明。
        // 另外，支付宝没有渠道退款单号，所以不用设置
        return PayRefundResultVo.createSuccessOfRefundResultVo(null, LocalDateTimeUtil.of(response.getGmtRefundPay()),
                payCreateRefundDto.getOutRefundNo(), response);
    }

    @Override
    public PayRefundResultVo doGetRefund(String outTradeNo, String outRefundNo) throws Throwable {
        // 构造请求参数以调用接口
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setOutTradeNo(outTradeNo);
        model.setOutRequestNo(outRefundNo);
        request.setBizModel(model);
        AlipayTradeFastpayRefundQueryResponse response;
        if (Objects.equals(config.getMode(), MODE_PUBLIC_KEY_AND_CERTIFICATE)) {
            // 证书模式
            response = client.certificateExecute(request);
        } else {
            response = client.execute(request);
        }
        if (!response.isSuccess()) {
            // 交易或者订单不存在 直接错误
            if (ObjectUtil.equalsAny("ACQ.TRADE_NOT_EXIST", "TRADE_NOT_EXIST")) {
                return PayRefundResultVo.createFailureOfRefundResultVo(outRefundNo, response);
            }
            return PayRefundResultVo.createWaitingOfRefundResultVo(null, outRefundNo, response);
        }
        return PayRefundResultVo.createSuccessOfRefundResultVo(null, LocalDateTimeUtil.of(response.getGmtRefundPay()),
                outRefundNo, response);
    }

    @Override
    protected PayRefundResultVo doParseRefundNotify(Map<String, String> params, String body) throws Throwable {
        // 补充说明：支付宝退款时，没有回调，这点和微信支付是不同的。并且，退款分成部分退款、和全部退款。
        // ① 部分退款：是会有回调，但是它回调的是订单状态的同步回调，不是退款订单的回调
        // ② 全部退款：Wap 支付有订单状态的同步回调，但是 PC/扫码又没有
        // 所以，这里在解析时，即使是退款导致的订单状态同步，我们也忽略不做为“退款同步”，而是订单的回调。
        // 实际上，支付宝退款只要发起成功，就可以认为退款成功，不需要等待回调。
        throw new UnsupportedOperationException("支付宝无退款回调");
    }

    @Override
    protected PayOrderResultVo doParseOrderNotify(Map<String, String> params, String body) throws Throwable {
        // 1. 校验回调数据 这些参数是放在请求url里面的
        Map<String, String> bodyObj = HttpUtil.decodeParamMap(body, StandardCharsets.UTF_8);
        AlipaySignature.rsaCheckV1(bodyObj, config.getAlipayPublicKey(),
                StandardCharsets.UTF_8.name(), config.getSignType());
        // 2. 解析订单的状态
        // 额外说明：支付宝不仅仅支付成功会回调，再各种触发支付单数据变化时，都会进行回调，所以这里 status 的解析会写的比较复杂
        Integer status = parseStatus(bodyObj.get("trade_status"));
        // 特殊逻辑: 支付宝没有退款成功的状态，所以，如果有退款金额，我们认为是退款成功
        if (MapUtil.getDouble(bodyObj, "refund_fee", 0D) > 0) {
            status = PAY_REFUND.getStatus();
        }
        Assert.notNull(status, (Supplier<Throwable>) () -> {
            throw new IllegalArgumentException(StrUtil.format("body({}) 的 trade_status 不正确", body));
        });
        return PayOrderResultVo.createAnyStatusOrderResultVo(status, bodyObj.get("trade_no"), bodyObj.get("seller_id"), parseTime(params.get("gmt_payment")),
                bodyObj.get("out_trade_no"), body);
    }

    protected String formatAmount(Integer amount) {
        return String.valueOf(amount / 100.0);
    }

    protected PayOrderResultVo buildClosedOrderResultVo(AlipayResponse response, String outTradeNo) {
        return PayOrderResultVo.createClosedOrderResultVo(
                response.getSubCode(), response.getSubMsg(),
                outTradeNo, response.getMsg()
        );
    }

    private static Integer parseStatus(String tradeStatus) {
        return Objects.equals("WAIT_BUYER_PAY", tradeStatus) ? PAY_WAITING.getStatus()
                : ObjectUtil.equalsAny(tradeStatus, "TRADE_FINISHED", "TRADE_SUCCESS") ? PAY_SUCCESS.getStatus()
                : Objects.equals("TRADE_CLOSED", tradeStatus) ? PAY_CLOSED.getStatus() : null;
    }

    protected LocalDateTime parseTime(String str) {
        return LocalDateTimeUtil.parse(str, NORM_DATETIME_FORMATTER);
    }
}
