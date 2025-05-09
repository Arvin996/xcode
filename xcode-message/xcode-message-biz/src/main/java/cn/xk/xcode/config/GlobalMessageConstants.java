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
    ErrorCode MESSAGE_CHANNEL_CODE_ALREADY_EXISTS = new IntErrorCode(1800_1_501, "message channel code:{} already exists");
    ErrorCode MESSAGE_CHANNEL_HAS_ACCOUNT = new IntErrorCode(1800_1_502, "message channel:{} has account, del failed");
    ErrorCode MESSAGE_CHANNEL_NOT_EXISTS = new IntErrorCode(1800_1_503, "message channel:{} not exists");
    ErrorCode MESSAGE_CHANNEL_ACCOUNT_NAME_ALREADY_EXISTS = new IntErrorCode(1800_1_504, "message channel account name:{} already exists");
    ErrorCode MESSAGE_TEMPLATE_ALREADY_EXISTS = new IntErrorCode(1800_1_505, "message template:{} already exists");
    ErrorCode MESSAGE_TEMPLATE_NOT_EXISTS = new IntErrorCode(1800_1_506, "message template:{} not exists");
    ErrorCode MESSAGE_TEMPLATE_CONTENT_NOT_DEFINED = new IntErrorCode(1800_1_508, "message template content not defined");
    ErrorCode MESSAGE_TEMPLATE_PARAMS_NOT_MATCH = new IntErrorCode(1800_1_507, "message template params not match");
    ErrorCode MESSAGE_TEMPLATE_CONFIG_ERROR = new IntErrorCode(1800_1_509, "message template config error, provided:{}");
    ErrorCode MESSAGE_TEMPLATE_PARAMS_VALUE_NOT_DEFINED = new IntErrorCode(1800_1_511, "message template params {} value not defined");
    ErrorCode MESSAGE_CONTENT_CONTAINS_SENSITIVE_WORDS =  new IntErrorCode(1800_1_512, "message content contains sensitive words");
    ErrorCode MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS = new IntErrorCode(1800_1_513, "message channel account not exists");
    ErrorCode MESSAGE_ACCOUNT_IS_DISABLED =  new IntErrorCode(1800_1_514, "message account is disabled");
    ErrorCode SMS_SEND_FAILED = new IntErrorCode(1800_1_515, "sms send failed");
    ErrorCode GE_TUI_CREATE_TASK_FAILED =  new IntErrorCode(1800_1_516, "GeTui create task failed");

    String XXL_THREAD_POOL_NAME = "execute-xxl-thread-pool";
}
