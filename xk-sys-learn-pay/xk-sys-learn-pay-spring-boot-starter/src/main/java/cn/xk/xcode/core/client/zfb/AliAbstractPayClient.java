package cn.xk.xcode.core.client.zfb;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.xk.xcode.core.client.AbstractPayClient;
import cn.xk.xcode.core.config.zfb.AliPayClientConfig;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.entity.refund.PayCreateRefundDto;
import cn.xk.xcode.entity.refund.PayRefundResultVo;
import cn.xk.xcode.utils.object.ObjectUtil;
import com.alipay.api.AlipayConfig;
import com.alipay.api.AlipayResponse;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import lombok.SneakyThrows;

import java.util.Objects;

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

    private Integer parseOrderStatus(String status){
       return Objects.equals("WAIT_BUYER_PAY", status) ? PAY_WAITING.getStatus()
               : ObjectUtil.equalsAny("TRADE_SUCCESS", "TRADE_FINISHED") ? PAY_SUCCESS.getStatus()
               : Objects.equals("TRADE_CLOSED", status) ? PAY_CLOSED.getStatus() : null;

    }

    @Override
    public PayRefundResultVo doCreateRefund(PayCreateRefundDto payCreateRefundDto) throws Throwable{
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
            if ("ACQ.SYSTEM_ERROR".equals(response.getSubCode())){
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
    public PayRefundResultVo doGetRefund(String outTradeNo, String outRefundNo) throws Throwable{
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

    protected String formatAmount(Integer amount) {
        return String.valueOf(amount / 100.0);
    }

    protected PayOrderResultVo buildClosedOrderResultVo(AlipayResponse response, String outTradeNo) {
        return PayOrderResultVo.createClosedOrderResultVo(
                response.getSubCode(), response.getSubMsg(),
                outTradeNo, response.getMsg()
        );
    }
}
