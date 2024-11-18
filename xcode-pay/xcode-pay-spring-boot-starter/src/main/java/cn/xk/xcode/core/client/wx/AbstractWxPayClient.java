package cn.xk.xcode.core.client.wx;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.date.TemporalAccessorUtil;
import cn.hutool.core.util.StrUtil;
import cn.xk.xcode.core.client.AbstractPayClient;
import cn.xk.xcode.core.config.wx.WxPayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.entity.refund.PayCreateRefundDto;
import cn.xk.xcode.entity.refund.PayRefundResultVo;
import cn.xk.xcode.enums.PayOrderStatusEnums;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServiceException;
import cn.xk.xcode.utils.file.FileUtil;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyV3Result;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.github.binarywang.wxpay.bean.request.*;
import com.github.binarywang.wxpay.bean.result.*;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;

import static cn.hutool.core.date.DatePattern.*;
import static cn.hutool.core.date.DatePattern.UTC_WITH_XXX_OFFSET_PATTERN;
import static cn.xk.xcode.core.config.wx.WxPayClientConfig.API_VERSION_V2;
import static cn.xk.xcode.core.config.wx.WxPayClientConfig.API_VERSION_V3;
import static cn.xk.xcode.enums.PayErrorCodeConstants.PAY_ORDER_STATUS_NOT_VALID;
import static cn.xk.xcode.enums.PayErrorCodeConstants.WX_SYSTEM_ERROR;

/**
 * @Author xuk
 * @Date 2024/9/9 9:34
 * @Version 1.0.0
 * @Description AbstractWxPayClient
 **/
@Slf4j
public abstract class AbstractWxPayClient extends AbstractPayClient<WxPayClientConfig> {

    protected WxPayService client;

    public AbstractWxPayClient(String channel, WxPayClientConfig config) {
        super(channel, config);
    }

    @Override
    public void doInit() {
        initClient(channel);
    }

    public void initClient(String tradeType) {
        // 创建 config 配置
        WxPayConfig payConfig = new WxPayConfig();
        BeanUtil.copyProperties(config, payConfig, "keyContent", "privateKeyContent", "privateCertContent");
        payConfig.setTradeType(tradeType);
        // weixin-pay-java 无法设置内容，只允许读取文件，所以这里要创建临时文件来解决
        // 这里也可以存数据库 todo
        if (Base64.isBase64(config.getKeyContent())) {
            payConfig.setKeyPath(FileUtil.createTempFile(Base64.decode(config.getKeyContent())).getPath());
        }
        if (StrUtil.isNotEmpty(config.getPrivateKeyContent())) {
            payConfig.setPrivateKeyPath(FileUtil.createTempFile(config.getPrivateKeyContent()).getPath());
        }
        if (StrUtil.isNotEmpty(config.getPrivateCertContent())) {
            payConfig.setPrivateCertPath(FileUtil.createTempFile(config.getPrivateCertContent()).getPath());
        }
        // 创建 client 客户端
        client = new WxPayServiceImpl();
        client.setConfig(payConfig);
    }

    @Override
    public PayOrderResultVo doCreateOrder(PayCreateOrderDto payCreateOrderDto) {
        // 兼容 v2 v3
        try {
            if (config.getApiVersion().equals(API_VERSION_V2)) {
                return doCreateOrderV2(payCreateOrderDto);
            } else if (config.getApiVersion().equals(API_VERSION_V3)) {
                return doCreateOrderV3(payCreateOrderDto);
            } else {
                throw new IllegalArgumentException(String.format("未知的 API 版本(%s)", config.getApiVersion()));
            }
        } catch (WxPayException e) {
            return PayOrderResultVo.createClosedOrderResultVo(getErrorCode(e), getErrorMessage(e), payCreateOrderDto.getOutTradeNo(), e.getXmlString());
        }
    }

    protected WxPayUnifiedOrderRequest buildPayUnifiedOrderRequestV2(PayCreateOrderDto payCreateOrderDto) {
        return WxPayUnifiedOrderRequest.newBuilder()
                .outTradeNo(payCreateOrderDto.getOutTradeNo())
                .body(payCreateOrderDto.getSubject())
                .detail(payCreateOrderDto.getBody())
                .totalFee(payCreateOrderDto.getPrice()) // 单位分
                .timeExpire(formatDateV2(payCreateOrderDto.getExpireTime()))
                .spbillCreateIp(payCreateOrderDto.getUserIp())
                .notifyUrl(payCreateOrderDto.getNotifyUrl())
                .build();
    }

    protected WxPayUnifiedOrderV3Request buildPayUnifiedOrderRequestV3(PayCreateOrderDto payCreateOrderDto) {
        WxPayUnifiedOrderV3Request request = new WxPayUnifiedOrderV3Request();
        request.setOutTradeNo(payCreateOrderDto.getOutTradeNo());
        request.setDescription(payCreateOrderDto.getSubject());
        request.setAmount(new WxPayUnifiedOrderV3Request.Amount().setTotal(payCreateOrderDto.getPrice())); // 单位分
        request.setTimeExpire(formatDateV3(payCreateOrderDto.getExpireTime()));
        request.setSceneInfo(new WxPayUnifiedOrderV3Request.SceneInfo().setPayerClientIp(payCreateOrderDto.getUserIp()));
        request.setNotifyUrl(payCreateOrderDto.getNotifyUrl());
        return request;
    }

    static String formatDateV2(LocalDateTime time) {
        return TemporalAccessorUtil.format(time.atZone(ZoneId.systemDefault()), PURE_DATETIME_PATTERN);
    }

    static LocalDateTime parseDateV2(String time) {
        return LocalDateTimeUtil.parse(time, PURE_DATETIME_PATTERN);
    }

    static String formatDateV3(LocalDateTime time) {
        return TemporalAccessorUtil.format(time.atZone(ZoneId.systemDefault()), UTC_WITH_XXX_OFFSET_PATTERN);
    }

    static LocalDateTime parseDateV3(String time) {
        return LocalDateTimeUtil.parse(time, UTC_WITH_XXX_OFFSET_PATTERN);
    }

    protected abstract PayOrderResultVo doCreateOrderV2(PayCreateOrderDto payCreateOrderDto) throws WxPayException;

    protected abstract PayOrderResultVo doCreateOrderV3(PayCreateOrderDto payCreateOrderDto) throws WxPayException;

    @Override
    public PayOrderResultVo doGetOrder(String outTradeNo) throws WxPayException {
        // 兼容 v2 v3
        try {
            if (config.getApiVersion().equals(API_VERSION_V2)) {
                return doGetOrderV2(outTradeNo);
            } else if (config.getApiVersion().equals(API_VERSION_V3)) {
                return doGetOrderV3(outTradeNo);
            } else {
                throw new IllegalArgumentException(String.format("未知的 API 版本(%s)", config.getApiVersion()));
            }
        } catch (WxPayException e) {
            if (ObjectUtil.equalsAny(e.getErrCode(), "ORDERNOTEXIST", "ORDER_NOT_EXIST")) {
                String errorCode = getErrorCode(e);
                String errorMessage = getErrorMessage(e);
                return PayOrderResultVo.createClosedOrderResultVo(errorCode, errorMessage,
                        outTradeNo, e.getXmlString());
            }
            throw e;
        }
    }

    private PayOrderResultVo doGetOrderV3(String outTradeNo) throws WxPayException {
        WxPayOrderQueryV3Request wxPayOrderQueryV3Request = new WxPayOrderQueryV3Request().setOutTradeNo(outTradeNo);
        WxPayOrderQueryV3Result response = client.queryOrderV3(wxPayOrderQueryV3Request);
        Integer status = parseOrderStatus(response.getTradeState());
        String openid = response.getPayer() != null ? response.getPayer().getOpenid() : null;
        return PayOrderResultVo.createAnyStatusOrderResultVo(status, response.getTransactionId(), openid,
                LocalDateTimeUtil.parse(response.getSuccessTime(), NORM_DATETIME_PATTERN), outTradeNo, response);
    }

    private PayOrderResultVo doGetOrderV2(String outTradeNo) throws WxPayException {
        WxPayOrderQueryRequest wxPayOrderQueryRequest = WxPayOrderQueryRequest.newBuilder().outTradeNo(outTradeNo).build();
        WxPayOrderQueryResult response = client.queryOrder(wxPayOrderQueryRequest);
        // 转换状态
        Integer status = parseOrderStatus(response.getTradeState());
        return PayOrderResultVo.createAnyStatusOrderResultVo(status, response.getTransactionId(), response.getOpenid(),
                LocalDateTimeUtil.parse(response.getTimeEnd(), NORM_DATETIME_PATTERN),
                outTradeNo, response);
    }

    private Integer parseOrderStatus(String statusName) {
        switch (statusName) {
            case "NOTPAY":
            case "USERPAYING": // 支付中，等待用户输入密码（条码支付独有）
                return PayOrderStatusEnums.PAY_WAITING.getStatus();
            case "SUCCESS":
                return PayOrderStatusEnums.PAY_SUCCESS.getStatus();
            case "REFUND":
                return PayOrderStatusEnums.PAY_REFUND.getStatus();
            case "CLOSED":
            case "REVOKED": // 已撤销（刷卡支付独有）
            case "PAYERROR": // 支付失败（其它原因，如银行返回失败）
                return PayOrderStatusEnums.PAY_CLOSED.getStatus();
            default:
                throw new ServiceException(PAY_ORDER_STATUS_NOT_VALID, statusName);
        }
    }

    @Override
    public PayRefundResultVo doCreateRefund(PayCreateRefundDto payCreateRefundDto) throws Throwable {
        try {
            switch (config.getApiVersion()) {
                case API_VERSION_V2:
                    return doCreateRefundV2(payCreateRefundDto);
                case WxPayClientConfig.API_VERSION_V3:
                    return doCreateRefundV3(payCreateRefundDto);
                default:
                    throw new IllegalArgumentException(String.format("未知的 API 版本(%s)", config.getApiVersion()));
            }
        } catch (WxPayException e) {
            if (ObjectUtil.equalsAny(e.getErrCode(), "REFUNDNOTEXIST", "RESOURCE_NOT_EXISTS")) {
                String errorCode = getErrorCode(e);
                String errorMessage = getErrorMessage(e);
                return PayRefundResultVo.createFailureOfRefundResultVo(errorCode, errorMessage,
                        payCreateRefundDto.getOutRefundNo(), e.getXmlString());
            }
            throw e;
        }
    }

    protected PayRefundResultVo doCreateRefundV2(PayCreateRefundDto payCreateRefundDto) throws WxPayException {
        WxPayRefundRequest request = new WxPayRefundRequest().setOutTradeNo(payCreateRefundDto.getOutTradeNo())
                .setOutRefundNo(payCreateRefundDto.getOutRefundNo())
                .setRefundFee(payCreateRefundDto.getRefundPrice())
                .setRefundDesc(payCreateRefundDto.getReason())
                .setNotifyUrl(payCreateRefundDto.getNotifyUrl())
                .setTotalFee(payCreateRefundDto.getPayPrice());
        WxPayRefundResult response = client.refund(request);
        if (!Objects.equals("SUCCESS", response.getReturnCode())) {
            ExceptionUtil.castServiceException(WX_SYSTEM_ERROR, response.getResultCode(), response.getErrCodeDes(), response.getErrorMessage());
        }
        // V2 情况下，不直接返回退款成功，而是等待异步通知
        if (Objects.equals("SUCCESS", response.getResultCode())) {
            return PayRefundResultVo.createWaitingOfRefundResultVo(response.getRefundId(),
                    payCreateRefundDto.getOutRefundNo(), response);
        }
        return PayRefundResultVo.createFailureOfRefundResultVo(payCreateRefundDto.getOutRefundNo(), response);
    }

    protected PayRefundResultVo doCreateRefundV3(PayCreateRefundDto payCreateRefundDto) throws WxPayException {
        WxPayRefundV3Request request = new WxPayRefundV3Request().setOutTradeNo(payCreateRefundDto.getOutTradeNo())
                .setOutRefundNo(payCreateRefundDto.getOutRefundNo())
                .setNotifyUrl(payCreateRefundDto.getNotifyUrl())
                .setReason(payCreateRefundDto.getReason())
                .setAmount(new WxPayRefundV3Request.Amount().setRefund(payCreateRefundDto.getRefundPrice())
                        .setTotal(payCreateRefundDto.getPayPrice()).setCurrency("CNY"));
        WxPayRefundV3Result response = client.refundV3(request);
        if (Objects.equals(response.getStatus(), "SUCCESS")) {
            return PayRefundResultVo.createSuccessOfRefundResultVo(response.getRefundId(), LocalDateTimeUtil.parse(
                    response.getSuccessTime(), NORM_DATETIME_PATTERN), payCreateRefundDto.getOutRefundNo(), response);
        }
        if (Objects.equals(response.getStatus(), "PROCESSING")) {
            return PayRefundResultVo.createWaitingOfRefundResultVo(response.getRefundId(), payCreateRefundDto.getOutRefundNo(), response);
        }
        return PayRefundResultVo.createFailureOfRefundResultVo(payCreateRefundDto.getOutRefundNo(), response);
    }

    @Override
    public PayRefundResultVo doGetRefund(String outTradeNo, String outRefundNo) throws Throwable {
        try {
            switch (config.getApiVersion()) {
                case API_VERSION_V2:
                    return doGetRefundV2(outTradeNo, outRefundNo);
                case WxPayClientConfig.API_VERSION_V3:
                    return doGetRefundV3(outTradeNo, outRefundNo);
                default:
                    throw new IllegalArgumentException(String.format("未知的 API 版本(%s)", config.getApiVersion()));
            }
        } catch (WxPayException e) {
            if (ObjectUtil.equalsAny(e.getErrCode(), "REFUNDNOTEXIST", "RESOURCE_NOT_EXISTS")) {
                String errorCode = getErrorCode(e);
                String errorMessage = getErrorMessage(e);
                return PayRefundResultVo.createFailureOfRefundResultVo(errorCode, errorMessage,
                        outRefundNo, e.getXmlString());
            }
            throw e;
        }
    }

    private PayRefundResultVo doGetRefundV2(String outTradeNo, String outRefundNo) throws WxPayException {
        WxPayRefundQueryRequest request = WxPayRefundQueryRequest.newBuilder().outTradeNo(outTradeNo).outRefundNo(outRefundNo).build();
        WxPayRefundQueryResult response = client.refundQuery(request);
        if (!Objects.equals("SUCCESS", response.getReturnCode())) {
            ExceptionUtil.castServiceException(WX_SYSTEM_ERROR, response.getResultCode(), response.getErrCodeDes(), response.getErrorMessage());
        }
        // 请求成功 但是没退费成功
        if (!"SUCCESS".equals(response.getResultCode())) {
            return PayRefundResultVo.createWaitingOfRefundResultVo(null, outRefundNo, response);
        }
        WxPayRefundQueryResult.RefundRecord refund = CollUtil.findOne(response.getRefundRecords(),
                record -> record.getOutRefundNo().equals(outRefundNo));
        if (refund == null) {
            return PayRefundResultVo.createFailureOfRefundResultVo(outRefundNo, response);
        }
        switch (refund.getRefundStatus()) {
            case "SUCCESS":
                return PayRefundResultVo.createSuccessOfRefundResultVo(refund.getRefundId(), LocalDateTimeUtil.parse(refund.getRefundSuccessTime(), NORM_DATETIME_PATTERN),
                        outRefundNo, response);
            case "PROCESSING":
                return PayRefundResultVo.createWaitingOfRefundResultVo(refund.getRefundId(),
                        outRefundNo, response);
            case "CHANGE": // 退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款
            case "FAIL":
                return PayRefundResultVo.createFailureOfRefundResultVo(outRefundNo, response);
            default:
                throw new IllegalArgumentException(String.format("未知的退款状态(%s)", refund.getRefundStatus()));
        }
    }

    private PayRefundResultVo doGetRefundV3(String outTradeNo, String outRefundNo) throws WxPayException {
        log.info(outTradeNo);
        WxPayRefundQueryV3Request request = new WxPayRefundQueryV3Request();
        request.setOutRefundNo(outRefundNo);
        WxPayRefundQueryV3Result response = client.refundQueryV3(request);
        switch (response.getStatus()) {
            case "SUCCESS":
                return PayRefundResultVo.createSuccessOfRefundResultVo(response.getRefundId(), LocalDateTimeUtil.parse(response.getSuccessTime(), NORM_DATETIME_PATTERN),
                        outRefundNo, response);
            case "PROCESSING":
                return PayRefundResultVo.createWaitingOfRefundResultVo(response.getRefundId(),
                        outRefundNo, response);
            case "ABNORMAL": // 退款异常
            case "CLOSED":
                return PayRefundResultVo.createFailureOfRefundResultVo(outRefundNo, response);
            default:
                throw new IllegalArgumentException(String.format("未知的退款状态(%s)", response.getStatus()));
        }
    }

    @Override
    protected PayOrderResultVo doParseOrderNotify(Map<String, String> params, String body) throws Throwable {
        switch (config.getApiVersion()) {
            case API_VERSION_V2:
                return doParseOrderNotifyV2(body);
            case WxPayClientConfig.API_VERSION_V3:
                return doParseOrderNotifyV3(body);
            default:
                throw new IllegalArgumentException(String.format("未知的 API 版本(%s)", config.getApiVersion()));
        }
    }

    private PayOrderResultVo doParseOrderNotifyV3(String body) throws WxPayException {
        // 1. 解析回调
        WxPayNotifyV3Result response = client.parseOrderNotifyV3Result(body, null);
        WxPayNotifyV3Result.DecryptNotifyResult result = response.getResult();
        // 2. 构建结果
        Integer status = parseStatus(result.getTradeState());
        String openid = result.getPayer() != null ? result.getPayer().getOpenid() : null;
        return PayOrderResultVo.createAnyStatusOrderResultVo(status, result.getTransactionId(), openid, parseDateV3(result.getSuccessTime()),
                result.getOutTradeNo(), body);
    }

    private PayOrderResultVo doParseOrderNotifyV2(String body) throws WxPayException {
        // 1. 解析回调 body是xml参数
        WxPayOrderNotifyResult response = client.parseOrderNotifyResult(body);
        // 2. 构建结果
        // V2 微信支付的回调，只有 SUCCESS 支付成功、CLOSED 支付失败两种情况，无需像支付宝一样解析的比较复杂
        Integer status = Objects.equals(response.getResultCode(), "SUCCESS") ?
                PayOrderStatusEnums.PAY_SUCCESS.getStatus() : PayOrderStatusEnums.PAY_CLOSED.getStatus();
        return PayOrderResultVo.createAnyStatusOrderResultVo(status, response.getTransactionId(), response.getOpenid(), parseDateV2(response.getTimeEnd()),
                response.getOutTradeNo(), body);
    }

    @Override
    protected PayRefundResultVo doParseRefundNotify(Map<String, String> params, String body) throws Throwable {
        switch (config.getApiVersion()) {
            case API_VERSION_V2:
                return doParseRefundNotifyV2(body);
            case WxPayClientConfig.API_VERSION_V3:
                return parseRefundNotifyV3(body);
            default:
                throw new IllegalArgumentException(String.format("未知的 API 版本(%s)", config.getApiVersion()));
        }
    }

    private PayRefundResultVo parseRefundNotifyV3(String body) throws WxPayException {
        // 1. 解析回调
        WxPayRefundNotifyV3Result response = client.parseRefundNotifyV3Result(body, null);
        WxPayRefundNotifyV3Result.DecryptNotifyResult result = response.getResult();
        // 2. 构建结果
        if (Objects.equals("SUCCESS", result.getRefundStatus())) {
            return PayRefundResultVo.createSuccessOfRefundResultVo(result.getRefundId(), parseDateV3(result.getSuccessTime()),
                    result.getOutRefundNo(), response);
        }
        return PayRefundResultVo.createFailureOfRefundResultVo(result.getOutRefundNo(), response);
    }

    private PayRefundResultVo doParseRefundNotifyV2(String body) throws WxPayException {
        // 1. 解析回调
        WxPayRefundNotifyResult response = client.parseRefundNotifyResult(body);
        WxPayRefundNotifyResult.ReqInfo result = response.getReqInfo();
        // 2. 构建结果
        if (Objects.equals("SUCCESS", result.getRefundStatus())) {
            return PayRefundResultVo.createSuccessOfRefundResultVo(result.getRefundId(), LocalDateTimeUtil.parse(result.getSuccessTime(), NORM_DATETIME_PATTERN),
                    result.getOutRefundNo(), response);
        }
        return PayRefundResultVo.createFailureOfRefundResultVo(result.getOutRefundNo(), response);
    }

    static String getErrorCode(WxPayException e) {
        if (StrUtil.isNotEmpty(e.getErrCode())) {
            return e.getErrCode();
        }
        if (StrUtil.isNotEmpty(e.getCustomErrorMsg())) {
            return "CUSTOM_ERROR";
        }
        return e.getReturnCode();
    }

    static String getErrorMessage(WxPayException e) {
        if (StrUtil.isNotEmpty(e.getErrCode())) {
            return e.getErrCodeDes();
        }
        if (StrUtil.isNotEmpty(e.getCustomErrorMsg())) {
            return e.getCustomErrorMsg();
        }
        return e.getReturnMsg();
    }

    private static Integer parseStatus(String tradeState) {
        switch (tradeState) {
            case "NOTPAY":
            case "USERPAYING": // 支付中，等待用户输入密码（条码支付独有）
                return PayOrderStatusEnums.PAY_WAITING.getStatus();
            case "SUCCESS":
                return PayOrderStatusEnums.PAY_SUCCESS.getStatus();
            case "REFUND":
                return PayOrderStatusEnums.PAY_REFUND.getStatus();
            case "CLOSED":
            case "REVOKED": // 已撤销（刷卡支付独有）
            case "PAYERROR": // 支付失败（其它原因，如银行返回失败）
                return PayOrderStatusEnums.PAY_CLOSED.getStatus();
            default:
                throw new IllegalArgumentException(StrUtil.format("未知的支付状态({})", tradeState));
        }
    }
}
