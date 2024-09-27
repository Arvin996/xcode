package cn.xk.xcode.enums;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2024/9/12 11:34
 * @Version 1.0.0
 * @Description PayModuleErrorCodeConstants
 **/
public interface PayModuleErrorCodeConstants {

    ErrorCode MERCHANT_NAME_ALREADY_EXIST = new IntErrorCode(14000_0_500, "商户名称%s已存在");
    ErrorCode MERCHANT_NO_ALREADY_EXIST = new IntErrorCode(14000_0_501, "商户编号%s已存在");
    ErrorCode APP_CODE_ALREADY_EXIST = new IntErrorCode(14000_0_502, "支付应用code%s已存在");
    ErrorCode APP_NAME_ALREADY_EXIST = new IntErrorCode(14000_0_503, "支付应用名称%s已存在");
    ErrorCode CHANNEL_CODE_ALREADY_EXIST = new IntErrorCode(14000_0_504, "支付渠道code%s已存在");
    ErrorCode APP_DELETE_FAILED = new IntErrorCode(14000_0_505, "删除失败，该支付应用已绑定支付渠道");
    ErrorCode CHANNEL_DELETE_FAILED = new IntErrorCode(14000_0_505, "删除失败，该支付渠道已绑定支付应用");
    ErrorCode PAY_APP_NOT_EXIST = new IntErrorCode(14000_0_506, "该支付渠道不存在");
    ErrorCode PAY_APP_IS_DISABLED = new IntErrorCode(14000_0_507, "该支付渠道%s已被禁用");
    ErrorCode PAY_ORDER_NOT_FOUND = new IntErrorCode(14000_0_508, "订单不存在");
    ErrorCode PAY_ORDER_ALREADY_PAID = new IntErrorCode(14000_0_509, "订单已支付成功");
    ErrorCode PAY_ORDER_STATUS_IS_NOT_WAITING = new IntErrorCode(14000_0_510, "订单已提交，请勿重复提交");
    ErrorCode ORDER_IS_ALREADY_EXPIRED = new IntErrorCode(14000_0_511, "订单已过期，请重新提交");
    ErrorCode PAY_CHANNEL_NOT_FOUND = new IntErrorCode(14000_0_512, "支付渠道%s不存在");
    ErrorCode PAY_CHANNEL_IS_DISABLED = new IntErrorCode(14000_0_513, "支付渠道%s已被禁用");
    ErrorCode PAY_APP_IS_NOT_CONTAINS_CHANNEL = new IntErrorCode(14000_0_514, "支付应用%s不包含支付渠道%s");
}
