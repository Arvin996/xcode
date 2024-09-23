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
}
