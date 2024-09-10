package cn.xk.xcode.core.client;

import cn.hutool.json.JSONUtil;
import cn.xk.xcode.core.config.PayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.entity.refund.PayCreateRefundDto;
import cn.xk.xcode.entity.refund.PayRefundResultVo;
import cn.xk.xcode.exception.core.ServerException;
import cn.xk.xcode.exception.core.ServiceException;
import com.alipay.api.AlipayApiException;
import lombok.extern.slf4j.Slf4j;

import static cn.xk.xcode.enums.PayErrorCodeConstants.*;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/9/6 21:16
 * @description
 */
@Slf4j
public abstract class AbstractPayClient<Config extends PayClientConfig> implements PayClient {

    protected String channel;
    protected Config config;

    public AbstractPayClient(String channel, Config config) {
        this.channel = channel;
        this.config = config;
    }

    public void reloadConfig(Config config) {
        this.config = config;
        initClient();
    }

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
    public PayOrderResultVo createOrder(PayCreateOrderDto payCreateOrderDto) throws Throwable {
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
    public PayOrderResultVo getOrder(String outTradeNo) throws Throwable{
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

    public abstract PayOrderResultVo doGetOrder(String outTradeNo) throws Throwable;

    @Override
    public PayRefundResultVo createRefund(PayCreateRefundDto payCreateRefundDto) throws Throwable{
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

    public abstract PayRefundResultVo doGetRefund(String outTradeNo, String outRefundNo) throws Throwable;
}
