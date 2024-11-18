package cn.xk.xcode.constant;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2024/10/29 16:28
 * @Version 1.0.0
 * @Description GlobalTakeoutUserConstant
 **/
public interface GlobalTakeoutUserConstant {

    ErrorCode ROLE_NAME_ALREADY_EXISTS = new IntErrorCode(1300_1_500, "角色名%s已存在");
    ErrorCode ROLE_HAS_BIND_USER = new IntErrorCode(1300_1_501, "角色已绑定用户, 删除失败");
    ErrorCode ROLE_HAS_BIND_PERMISSION = new IntErrorCode(1300_1_502, "角色已绑定权限, 删除失败");
    ErrorCode PERMISSION_CODE_ALREADY_EXISTS = new IntErrorCode(1300_1_503, "权限编码%s已存在");
    ErrorCode USER_NOT_EXISTS = new IntErrorCode(1300_1_504, "用户不存在");
    ErrorCode MOBILE_HAS_USED = new IntErrorCode(1300_1_505, "手机号%s已被使用");
    ErrorCode ORIGINAL_PASSWORD_ERROR = new IntErrorCode(1300_1_506, "原密码错误");
    ErrorCode ROLE_NOT_EXISTS = new IntErrorCode(1300_1_507, "角色不存在");
    ErrorCode CONFIRM_PASSWORD_NOT_EQUAL = new IntErrorCode(1300_1_508, "两次输入密码不一致");
    ErrorCode ACCOUNT_OR_PASSWORD_ERROR = new IntErrorCode(1300_1_509, "账号或密码错误");
    ErrorCode MOBILE_NOT_EXISTS = new IntErrorCode(1300_1_510, "手机号%s不存在");
    ErrorCode ACCOUNT_IS_DISABLED =  new IntErrorCode(1300_1_511, "账号已被禁用");
}
