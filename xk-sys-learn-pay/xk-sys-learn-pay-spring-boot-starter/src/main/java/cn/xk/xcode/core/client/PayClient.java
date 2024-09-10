package cn.xk.xcode.core.client;

import cn.xk.xcode.entity.order.PayCreateOrderDto;
import cn.xk.xcode.entity.order.PayOrderResultVo;
import cn.xk.xcode.entity.refund.PayCreateRefundDto;
import cn.xk.xcode.entity.refund.PayRefundResultVo;

/**
 * @Author xuk
 * @Date 2024/9/3 16:28
 * @Version 1.0.0
 * @Description PayClient
 **/
public interface PayClient {

    // 支付渠道 如微信app 当面付主扫 被扫等
    String channel();

    // 创建订单
    PayOrderResultVo createOrder(PayCreateOrderDto payCreateOrderDto) throws Throwable;

    // 获取订单信息
    PayOrderResultVo getOrder(String outTradeNo) throws Throwable;

    // 创建退款
    PayRefundResultVo createRefund(PayCreateRefundDto payCreateRefundDto) throws Throwable;

    // 获取退款信息
    PayRefundResultVo getRefund(String outTradeNo, String outRefundNo) throws Throwable;
}
