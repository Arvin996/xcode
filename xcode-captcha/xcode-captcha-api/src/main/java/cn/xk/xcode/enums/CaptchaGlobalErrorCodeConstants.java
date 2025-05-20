package cn.xk.xcode.enums;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2024/9/23 14:08
 * @Version 1.0.0
 * @Description CaptchaGlobalErrorCodeConstants
 **/
public interface CaptchaGlobalErrorCodeConstants {


    ErrorCode CHECK_CODE_IS_EXPIRED = new IntErrorCode(4002_1_500, "验证码已过期");
    ErrorCode CHECK_CODE_IS_ERROR = new IntErrorCode(4002_1_501, "验证码不正确");
    ErrorCode CAPTCHA_REPEAT_SEND = new IntErrorCode(4002_1_502, "验证码发送过于频繁");
    ErrorCode SEND_CAPTCHA_ERROR = new IntErrorCode(4002_1_503, "验证码发送失败");
    ErrorCode EMAIL_MUST_NOT_NULL =  new IntErrorCode(4002_1_505, "邮箱不能为空");
    ErrorCode MOBILE_MUST_NOT_NULL =  new IntErrorCode(4002_1_505, "手机号不能为空");
}
