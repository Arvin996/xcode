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
    ErrorCode PARAMETER_VALIDATION_FAIL = new IntErrorCode(418, "参数解析异常");
    ErrorCode ERROR_CODE_MESSAGE_PLACE_HOLDER_RESOLVE_ERROR = new IntErrorCode(419, "错误码信息占位符个哦实话异常");
    ErrorCode QR_CODE_GEN_ERROR = new IntErrorCode(420, "生成二维码错误");
    ErrorCode READ_HTTP_BODY_ERROR =  new IntErrorCode(421, "读取http body失败, 异常信息:{}");

    ErrorCode LOCKED = new IntErrorCode(423, "请求失败，请稍后重试"); // 并发请求，不允许
    ErrorCode TOO_MANY_REQUESTS = new IntErrorCode(429, "请求过于频繁，请稍后重试");
    ErrorCode CACHE_TYPE_INVALID = new IntErrorCode(430, "无效缓存类型");
    // ========== 服务端错误段 ==========
    ErrorCode INTERNAL_SERVER_ERROR = new IntErrorCode(500, "系统异常");
    ErrorCode NOT_IMPLEMENTED = new IntErrorCode(501, "功能未实现/未开启");
    ErrorCode ERROR_CONFIGURATION = new IntErrorCode(502, "错误的配置项");
    ErrorCode CHECK_CODE_IS_EXPIRED = new IntErrorCode(503, "验证码已过期");
    ErrorCode CHECK_CODE_IS_ERROR = new IntErrorCode(504, "验证码不正确");
    ErrorCode CHECK_CODE_GEN_ERROR = new IntErrorCode(505, "验证码生成失败");
    ErrorCode CHECK_CODE_SEND_ERROR = new IntErrorCode(506, "验证码发送失败");
    ErrorCode CHECK_CODE_EMAIL_NOT_CONFIG = new IntErrorCode(507, "验证码邮箱未配置");
    ErrorCode CHECK_CODE_MOBILE_NOT_CONFIG = new IntErrorCode(508, "验证码手机短信未配置");
    ErrorCode CHECK_CODE_HANDLER_NOT_EXISTS = new IntErrorCode(509, "对应的验证码处理器不存在");
    ErrorCode MQ_MESSAGE_SEND_FAIL = new IntErrorCode(510, "消息发送失败");
    ErrorCode SERVICE_FALL_BACK = new IntErrorCode(511, "服务熔断");
    // ========== 自定义错误段 ==========
    ErrorCode REPEATED_REQUESTS = new IntErrorCode(900, "重复请求，请稍后重试"); // 重复请求
    ErrorCode DEMO_DENY = new IntErrorCode(901, "演示模式，禁止写操作");
    ErrorCode EXCEL_MESSAGE_ADD_ERROR = new IntErrorCode(902, "往excel导出队列中添加消息失败");
    IntErrorCode EXCEL_EXPORT_ERROR = new IntErrorCode(903, "导出Excel失败");
    ErrorCode UNKNOWN = new IntErrorCode(999, "未知错误");
    ErrorCode BUNDLE_MESSAGE_NOT_FOUND = new IntErrorCode(998, "对应的国际化资源文件未找到信息code{}");
    ErrorCode TASK_CONTEXT_IS_NULL =  new IntErrorCode(997, "任务上下文为空");
    ErrorCode TASK_CONTEXT_BIZ_CODE_IS_NULL =  new IntErrorCode(996, "任务上下文业务编码为空");
    ErrorCode TASK_HANDLER_LIST_IS_NULL =  new IntErrorCode(995, "任务编码{}对应的任务执行器列表为空");
}
