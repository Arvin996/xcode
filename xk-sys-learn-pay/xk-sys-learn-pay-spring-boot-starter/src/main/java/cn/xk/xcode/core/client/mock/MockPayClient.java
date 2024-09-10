package cn.xk.xcode.core.client.mock;

import cn.xk.xcode.core.client.AbstractPayClient;
import cn.xk.xcode.core.config.DefaultPayClientConfig;
import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.entity.refund.PayCreateRefundDto;
import cn.xk.xcode.entity.refund.PayRefundResultVo;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author xuk
 * @Date 2024/9/9 11:18
 * @Version 1.0.0
 * @Description MockPayClient
 **/
@Slf4j
public class MockPayClient extends AbstractPayClient<DefaultPayClientConfig> {

    public MockPayClient(String channel, DefaultPayClientConfig config) {
        super(channel, config);
    }

    @Override
    public void doInit() {
        log.info("模拟测试支付客户端初始化完成---------");
    }

    @Override
    public PayOrderResultVo doCreateOrder(PayCreateOrderDto payCreateOrderDto) throws Throwable {
        // 直接返回即可
        log.info("模拟测试支付客户端创建订单完成---------");
        return null;
    }

    @Override
    public PayOrderResultVo doGetOrder(String outTradeNo) throws Throwable {
        // 直接返回即可
        log.info("模拟测试支付客户端查询订单完成---------");
        return null;
    }

    @Override
    public PayRefundResultVo doCreateRefund(PayCreateRefundDto payCreateRefundDto) throws Throwable {
        // 直接返回即可
        log.info("模拟测试支付客户端创建退款完成---------");
        return null;
    }

    @Override
    public PayRefundResultVo doGetRefund(String outTradeNo, String outRefundNo) throws Throwable {
        // 直接返回即可
        log.info("模拟测试支付客户端查询退款完成---------");
        return null;
    }
}
