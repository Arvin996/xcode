package cn.xk.xcode.config;

import cn.xk.xcode.exception.ErrorCode;
import cn.xk.xcode.exception.IntErrorCode;

/**
 * @Author xuk
 * @Date 2025/3/10 9:25
 * @Version 1.0.0
 * @Description GlobalMessageConstants
 **/
public interface GlobalMessageConstants {

    ErrorCode MESSAGE_CHANNEL_NAME_ALREADY_EXISTS = new IntErrorCode(1800_1_500, "message channel name:{} already exists");
    ErrorCode MESSAGE_CHANNEL_HAS_ACCOUNT = new IntErrorCode(1800_1_502, "message channel:{} has account, del failed");
    ErrorCode MESSAGE_CHANNEL_NOT_EXISTS = new IntErrorCode(1800_1_503, "message channel:{} not exists");
    ErrorCode MESSAGE_CHANNEL_ACCOUNT_NAME_ALREADY_EXISTS = new IntErrorCode(1800_1_504, "message channel account name:{} already exists");
    ErrorCode MESSAGE_TEMPLATE_ALREADY_EXISTS = new IntErrorCode(1800_1_505, "message template:{} already exists");
    ErrorCode MESSAGE_TEMPLATE_NOT_EXISTS = new IntErrorCode(1800_1_506, "message template:{} not exists");
    ErrorCode MESSAGE_TEMPLATE_CONTENT_NOT_DEFINED = new IntErrorCode(1800_1_508, "message template content not defined");
    ErrorCode MESSAGE_TEMPLATE_PARAMS_NOT_MATCH = new IntErrorCode(1800_1_507, "message template params not match");
    ErrorCode MESSAGE_TEMPLATE_CONFIG_ERROR = new IntErrorCode(1800_1_509, "message template config error, provided:{}");
    ErrorCode MESSAGE_TEMPLATE_PARAMS_VALUE_NOT_DEFINED = new IntErrorCode(1800_1_511, "message template params {} value not defined");
    ErrorCode MESSAGE_CONTENT_CONTAINS_SENSITIVE_WORDS = new IntErrorCode(1800_1_512, "message content contains sensitive words");
    ErrorCode MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS = new IntErrorCode(1800_1_513, "message channel account not exists");
    ErrorCode MESSAGE_ACCOUNT_IS_DISABLED = new IntErrorCode(1800_1_514, "message account is disabled");
    ErrorCode SMS_SEND_FAILED = new IntErrorCode(1800_1_515, "sms send failed");
    ErrorCode GE_TUI_CREATE_TASK_FAILED = new IntErrorCode(1800_1_516, "GeTui create task failed");

    // =============================== 新
    ErrorCode CLIENT_NAME_ALREADY_EXISTS = new IntErrorCode(4003_1_500, "该接入商名{}已被使用");
    ErrorCode MOBILE_AND_EMAIL_BOTH_NULL = new IntErrorCode(4003_1_501, "手机号和邮箱不能同时为空");
    ErrorCode CLIENT_NOT_EXISTS = new IntErrorCode(4003_1_502, "该接入商不存在");
    ErrorCode CLIENT_NOT_ENABLED = new IntErrorCode(4003_1_503, "该接入商未启用");
    ErrorCode CLIENT_TOKEN_ERROR = new IntErrorCode(4003_1_504, "接入商token错误");
    ErrorCode CHANNEL_ACCOUNT_NAME_ALREADY_EXISTS = new IntErrorCode(4003_1_505, "该渠道的账户名{}已被使用");
    ErrorCode CHANNEL_NOT_EXISTS = new IntErrorCode(4003_1_506, "渠道不存在");
    ErrorCode MESSAGE_CHANNEL_PARAM_IS_REQUIRED = new IntErrorCode(4003_1_507, "渠道{}的参数{}是必填项");
    ErrorCode MESSAGE_CHANNEL_PARAM_MISSING = new IntErrorCode(4003_1_508, "渠道{}的参数{}缺失");
    ErrorCode MESSAGE_CHANNEL_PARAM_ALREADY_EXISTS = new IntErrorCode(4003_1_509, "该渠道的参数{}已存在");
    ErrorCode MESSAGE_CHANNEL_PARAM_NOT_EXISTS = new IntErrorCode(4003_1_510, "该渠道的此参数不存在");
    ErrorCode MESSAGE_CHANNEL_CODE_ALREADY_EXISTS = new IntErrorCode(4003_1_511, "渠道码{}已被使用");
    ErrorCode MESSAGE_CHANNEL_HAS_BIND_ACCOUNT =  new IntErrorCode(4003_1_512, "删除失败，该渠道已绑定账户");
    ErrorCode MESSAGE_CHANNEL_HAS_BIND_PARAM =  new IntErrorCode(4003_1_512, "删除失败，该渠道已绑定参数");
    ErrorCode MESSAGE_TEMPLATE_ID_HAS_DEFINED =  new IntErrorCode(4003_1_513, "该模板id:{}已被使用");
    ErrorCode MASSAGE_TEMPLATE_NAME_ALREADY_EXISTS =  new IntErrorCode(4003_1_514, "该模板名:{}已被使用");
    ErrorCode MESSAGE_TEMPLATE_HAS_DEFINED_THIS_PARAM =  new IntErrorCode(4003_1_515, "该模板已定义了此参数:{}");

    String XXL_THREAD_POOL_NAME = "execute-xxl-thread-pool";
    int MESSAGE_ACCESS_CLIENT_TOKEN_EXPIRED_TIME = 24 * 60 * 40 * 30; // 一个月
}
