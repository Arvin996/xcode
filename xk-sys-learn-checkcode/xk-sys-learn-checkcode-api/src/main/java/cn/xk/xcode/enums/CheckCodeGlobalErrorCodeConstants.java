package cn.xk.xcode.enums;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2024/9/23 14:08
 * @Version 1.0.0
 * @Description CheckCodeGlobalErrorCodeConstants
 **/
public interface CheckCodeGlobalErrorCodeConstants {

    ErrorCode MOBILE_CONFIG_PROPERTY_IS_NULL = new IntErrorCode(14000_1_500, "验证码手机短信配置属性%s为空");
    ErrorCode EMAIL_CONFIG_PROPERTY_IS_NULL = new IntErrorCode(14000_1_501, "验证码邮箱短信配置属性%s为空");
}
