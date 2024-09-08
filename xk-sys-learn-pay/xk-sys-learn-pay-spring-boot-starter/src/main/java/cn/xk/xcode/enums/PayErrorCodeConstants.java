package cn.xk.xcode.enums;

import cn.xk.xcode.exception.IntErrorCode;

/**
 * @author xukai
 * @version 1.0
 * @date 2024/9/6 21:34
 * @description
 */
public interface PayErrorCodeConstants {
    IntErrorCode CREATE_ORDER_FILED = new IntErrorCode(19000_0_500, "客户端渠道{}创建订单失败, 请求信息：{}");
    IntErrorCode QUERY_ORDER_FILED = new IntErrorCode(19000_0_501, "客户端渠道{}查询订单失败, 订单号：{}");
    IntErrorCode CREATE_ORDER_REFUND_FILED = new IntErrorCode(19000_0_502, "客户端渠道{}创建退费订单失败, 订单号：{}， 外部订单号{}，请求信息：{}");
    IntErrorCode QUERY_REFUND_ORDER_FILED = new IntErrorCode(19000_0_503, "查询退费订单失败，订单号：{}，退费订单号：{}");
    IntErrorCode PAY_CLIENT_NOT_EXISTS = new IntErrorCode(19000_0_504, "支付客户端{}不存在");
}
