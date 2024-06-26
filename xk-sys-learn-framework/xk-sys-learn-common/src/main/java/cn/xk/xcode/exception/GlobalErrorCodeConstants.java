package cn.xk.xcode.exception;

/**
 * @Author xuk
 * @Date 2024/5/27 13:47
 * @Version 1.0
 * @Description GlobalErrorCodeConstants
 */
public interface GlobalErrorCodeConstants
{
    ErrorCode SUCCESS = new IntErrorCode(0, "成功");
    // ========== 客户端错误段 ==========
    ErrorCode BAD_REQUEST = new IntErrorCode(400, "请求参数不正确");
    ErrorCode UNAUTHORIZED = new IntErrorCode(401, "账号未登录");
    ErrorCode FORBIDDEN = new IntErrorCode(403, "没有该操作权限");
    ErrorCode NOT_FOUND = new IntErrorCode(404, "请求未找到");
    ErrorCode METHOD_NOT_ALLOWED = new IntErrorCode(405, "请求方法不正确");
    ErrorCode QUERY_FAILED = new IntErrorCode(409, "用户不存在");
    ErrorCode USER_ADD_FAILED = new IntErrorCode(410, "该用户名已存在"); // 并发请求，不允许
    ErrorCode ROLE_ADD_FAILED = new IntErrorCode(411, "该角色已存在");
    ErrorCode INVALID_TOKEN = new IntErrorCode(412, "无效token");
    ErrorCode INVALID_GRANT_TYPE = new IntErrorCode(413, "无效授权类型");
    ErrorCode INVALID_CLIENT = new IntErrorCode(414, "无效客户端");
    ErrorCode INVALID_USERNAME_OR_PASSWORD = new IntErrorCode(416, "用户名或密码错误");
    ErrorCode ACCOUNT_NOT_ENABLE = new IntErrorCode(417, "该账号已被封禁，请联系管理员");

    ErrorCode LOCKED = new IntErrorCode(423, "请求失败，请稍后重试"); // 并发请求，不允许
    ErrorCode TOO_MANY_REQUESTS = new IntErrorCode(429, "请求过于频繁，请稍后重试");
    // ========== 服务端错误段 ==========
    ErrorCode INTERNAL_SERVER_ERROR = new IntErrorCode(500, "系统异常");
    ErrorCode NOT_IMPLEMENTED = new IntErrorCode(501, "功能未实现/未开启");
    ErrorCode ERROR_CONFIGURATION = new IntErrorCode(502, "错误的配置项");
    // ========== 自定义错误段 ==========
    ErrorCode REPEATED_REQUESTS = new IntErrorCode(900, "重复请求，请稍后重试"); // 重复请求
    ErrorCode DEMO_DENY = new IntErrorCode(901, "演示模式，禁止写操作");
    ErrorCode UNKNOWN = new IntErrorCode(999, "未知错误");
}
