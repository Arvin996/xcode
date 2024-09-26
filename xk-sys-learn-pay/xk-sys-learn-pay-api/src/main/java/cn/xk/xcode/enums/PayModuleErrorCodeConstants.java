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
}
