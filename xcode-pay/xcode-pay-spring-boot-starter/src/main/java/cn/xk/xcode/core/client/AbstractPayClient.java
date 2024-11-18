package cn.xk.xcode.core.client;

import cn.hutool.json.JSONUtil;
import cn.xk.xcode.core.config.PayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.entity.refund.PayCreateRefundDto;
import cn.xk.xcode.entity.refund.PayRefundResultVo;
import cn.xk.xcode.exception.core.ExceptionUtil;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.exception.core.ServiceException;
import com.alipay.api.AlipayApiException;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static cn.xk.xcode.enums.PayErrorCodeConstants.*;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/9/6 21:16
 * @description
 */
@Data
@Slf4j
public abstract class AbstractPayClient<Config extends PayClientConfig> implements PayClient {

    protected String channel;
    protected Config config;

    public AbstractPayClient(String channel, Config config) {
        this.channel = channel;
        this.config = config;
    }

//    public void reloadConfig(Config config) {
//        this.config = config;
//        initClient();
//    }

    private void initClient() {
        log.info("wx支付客户端{} 开始初始化", this.getClass().getSimpleName());
        doInit();
        log.info("wx支付客户端{} init success", this.getClass().getSimpleName());
    }

    public abstract void doInit();

    @Override
    public String channel() {
        return channel;
    }

    @Override
    public PayOrderResultVo createOrder(PayCreateOrderDto payCreateOrderDto){
        PayOrderResultVo payOrderResultVo;
        try {
            payOrderResultVo = doCreateOrder(payCreateOrderDto);
        } catch (ServiceException e) {
            throw e;
        } catch (Throwable e) {
            log.error("创建订单失败，失败信息：{}", e.getMessage());
            throw new ServerException(CREATE_ORDER_FILED, channel, JSONUtil.toJsonStr(payCreateOrderDto));
        }
        return payOrderResultVo;
    }

    public abstract PayOrderResultVo doCreateOrder(PayCreateOrderDto payCreateOrderDto) throws Throwable;

    @Override
    public PayOrderResultVo getOrder(String outTradeNo) {
        PayOrderResultVo payOrderResultVo;
        try {
            payOrderResultVo = doGetOrder(outTradeNo);
        } catch (ServiceException e) {
            throw e;
        } catch (Throwable e) {
            log.error("查询订单失败，订单号：{}，失败信息：{}", outTradeNo, e.getMessage());
            throw new ServerException(QUERY_ORDER_FILED, channel, outTradeNo);
        }
        return payOrderResultVo;
    }

    @Override
    public PayOrderResultVo parseOrderNotify(Map<String, String> params, String body) {
        try {
            return doParseOrderNotify(params, body);
        } catch (ServiceException ex) { // 业务异常，都是实现类已经翻译，所以直接抛出即可
            throw ex;
        } catch (Throwable ex) {
            log.error("[parseOrderNotify][客户端({}) params({}) body({}) 解析失败]",
                    channel, params, body, ex);
           throw new ServiceException(PARSE_ORDER_NOTIFY_FAILED, ex.getMessage());
        }
    }

    protected abstract PayOrderResultVo doParseOrderNotify(Map<String, String> params, String body)
            throws Throwable;

    public abstract PayOrderResultVo doGetOrder(String outTradeNo) throws Throwable;

    @Override
    public PayRefundResultVo createRefund(PayCreateRefundDto payCreateRefundDto) {
        PayRefundResultVo payRefundResultVo;
        try {
            payRefundResultVo = doCreateRefund(payCreateRefundDto);
        } catch (ServiceException e) {
            throw e;
        } catch (Throwable e) {
            log.error("创建退费订单失败，订单号：{}，退费单号：{}， 失败信息：{}", payCreateRefundDto.getOutRefundNo(), payCreateRefundDto.getOutRefundNo(), e.getMessage());
            throw new ServerException(CREATE_ORDER_REFUND_FILED, payCreateRefundDto.getOutRefundNo(), payCreateRefundDto.getOutRefundNo(), JSONUtil.toJsonStr(payCreateRefundDto));
        }
        return payRefundResultVo;
    }

    public abstract PayRefundResultVo doCreateRefund(PayCreateRefundDto payCreateRefundDto) throws Throwable;

    @Override
    public PayRefundResultVo getRefund(String outTradeNo, String outRefundNo) throws Throwable{
        PayRefundResultVo payRefundResultVo;
        try {
            payRefundResultVo = doGetRefund(outTradeNo, outRefundNo);
        } catch (ServiceException e) {
            throw e;
        } catch (Throwable e) {
            log.error("查询退费订单失败，订单号：{}，退费订单号：{}，失败信息：{}", outTradeNo, outRefundNo, e.getMessage());
            throw new ServerException(QUERY_REFUND_ORDER_FILED, outTradeNo, outRefundNo);
        }
        return payRefundResultVo;
    }

    @Override
    public PayRefundResultVo parseRefundNotify(Map<String, String> params, String body) {
        try {
            return doParseRefundNotify(params, body);
        } catch (ServiceException ex) { // 业务异常，都是实现类已经翻译，所以直接抛出即可
            throw ex;
        } catch (Throwable ex) {
            log.error("[parseRefundNotify][客户端({}) params({}) body({}) 解析失败]",
                    channel, params, body, ex);
            throw new ServiceException(PARSE_REFUND_NOTIFY_FAILED, ex.getMessage());
        }
    }

    protected abstract PayRefundResultVo doParseRefundNotify(Map<String, String> params, String body)
            throws Throwable;

    public abstract PayRefundResultVo doGetRefund(String outTradeNo, String outRefundNo) throws Throwable;
}
