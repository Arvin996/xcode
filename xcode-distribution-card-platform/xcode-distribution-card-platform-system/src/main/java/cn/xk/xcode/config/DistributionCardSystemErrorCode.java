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
    ErrorCode PASSWORD_CONFIRM_NOT_EQUAL = new IntErrorCode(4001_0_501, "两次输入密码不一致");
    ErrorCode USERNAME_OR_PASSWORD_ERROR = new IntErrorCode(4001_0_502, "用户名或密码错误");
    ErrorCode ACCOUNT_IS_DISABLED = new IntErrorCode(4001_0_503, "账号已被禁用, 请联系管理员");
    ErrorCode SERVICE_API_CODE_ALREADY_EXISTS = new IntErrorCode(4001_0_504, "服务{}接口权限标识{}已存在");
    ErrorCode SERVICE_API_PATH_ALREADY_EXISTS = new IntErrorCode(4001_0_505, "服务{}接口路径{}已存在");
    ErrorCode API_HAS_BINDING_ROLE = new IntErrorCode(4001_0_506, "该接口已绑定角色, 请解绑后再删除");
    ErrorCode DICT_TYPE_ALREADY_EXISTS = new IntErrorCode(4001_0_507, "字典类型{}已存在");
    ErrorCode DICT_TYPE_NOT_EXISTS = new IntErrorCode(4001_0_508, "字典类型{}不存在");
    ErrorCode DICT_TYPE_IS_DISABLED = new IntErrorCode(4001_0_509, "字典类型{}已被禁用");
    ErrorCode DICT_TYPE_DATA_ALREADY_EXISTS = new IntErrorCode(4001_0_510, "字典类型{}数据{}已存在");
    ErrorCode MENU_ALREADY_EXISTS = new IntErrorCode(4001_0_511, "菜单{}已存在");
    ErrorCode MENU_NOT_EXISTS = new IntErrorCode(4001_0_512, "菜单不存在");
    ErrorCode DICT_DATA_NOT_EXISTS = new IntErrorCode(4001_0_513, "字典数据不存在");
    ErrorCode ROLE_NAME_ALREADY_EXISTS = new IntErrorCode(4001_0_514, "角色名{}已存在");
    ErrorCode ROLE_CODE_ALREADY_EXISTS = new IntErrorCode(4001_0_515, "角色权限{}已存在");
    ErrorCode ROLE_HAS_BINDING_USER = new IntErrorCode(4001_0_516, "该角色下存在已绑定用户，无法删除");
    ErrorCode USER_NOT_EXISTS = new IntErrorCode(4001_0_517, "用户不存在");
    ErrorCode ORIGINAL_PASSWORD_ERROR = new IntErrorCode(4001_0_518, "原密码错误");
    ErrorCode NEW_OLD_PASSWORD_MUST_NOT_EQUAL = new IntErrorCode(4001_0_519, "新密码不能与原密码相同");
    ErrorCode NEW_OLD_PASSWORD_SIMILARITY = new IntErrorCode(4001_0_520, "新密码与原密码太过于相似");
    ErrorCode NOTICE_TO_USER_MUST_NOT_EMPTY = new IntErrorCode(4001_0_521, "通知接收人不能为空");
    ErrorCode LOGIN_EMAIL_MUST_NOT_EMPTY = new IntErrorCode(4001_0_522, "登录邮箱不能为空");
}
