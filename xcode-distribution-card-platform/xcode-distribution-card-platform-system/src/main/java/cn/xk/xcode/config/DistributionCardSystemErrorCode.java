package cn.xk.xcode.config;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2025/5/9 11:02
 * @Version 1.0.0
 * @Description DistributionCardSystemErrorCode
 **/
public interface DistributionCardSystemErrorCode {

    ErrorCode USERNAME_ALREADY_EXISTS = new IntErrorCode(4001_0_500, "用户名已存在");
    ErrorCode PASSWORD_CONFIRM_NOT_EQUAL =  new IntErrorCode(4001_0_501, "两次输入密码不一致");
    ErrorCode USERNAME_OR_PASSWORD_ERROR = new  IntErrorCode(4001_0_502, "用户名或密码错误");
    ErrorCode ACCOUNT_IS_DISABLED = new IntErrorCode(4001_0_503, "账号已被禁用, 请联系管理员");
}
