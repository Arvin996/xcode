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
    IntErrorCode PAY_ORDER_STATUS_NOT_VALID = new IntErrorCode(19000_0_505, "支付状态{}不存在");
    IntErrorCode PAY_ORDER_RESPONSE_BODY_IS_NOT_CORRECT = new IntErrorCode(19000_0_505, "body({}) 的 trade_status 不正确");
    IntErrorCode WX_SYSTEM_ERROR = new IntErrorCode(19000_0_506, "wx接口调用失败，错误码：{}, 错误码信息描述：{},错误信息：{}");
    IntErrorCode AUTH_BAR_CODE_MUST_NOT_NULL = new IntErrorCode(19000_0_507, "扫码支付的条形码code不能为空");
    IntErrorCode JSAPI_REQUEST_OPENID_MUST_NOT_NULL = new IntErrorCode(19000_0_508, "微信jsapi支付的openid不能为空");
}
