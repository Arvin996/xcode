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
    ErrorCode MESSAGE_CHANNEL_ACCOUNT_NAME_ALREADY_EXISTS = new IntErrorCode(1800_1_504, "message channel account name:{} already exists");
    ErrorCode MESSAGE_TEMPLATE_ALREADY_EXISTS = new IntErrorCode(1800_1_505, "message template:{} already exists");
    ErrorCode MESSAGE_TEMPLATE_CONFIG_ERROR = new IntErrorCode(1800_1_509, "message template config error, provided:{}");
    ErrorCode MESSAGE_TEMPLATE_PARAMS_VALUE_NOT_DEFINED = new IntErrorCode(1800_1_511, "message template params {} value not defined");
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
    ErrorCode MESSAGE_TEMPLATE_NOT_EXISTS = new IntErrorCode(4003_1_516, "消息模板不存在");
    ErrorCode MESSAGE_TEMPLATE_CONTENT_NOT_DEFINED = new IntErrorCode(4003_1_517, "模板消息内容未定义");
    ErrorCode MESSAGE_TEMPLATE_PARAMS_NOT_MATCH = new IntErrorCode(4003_1_518, "消息模板参数值不匹配");
    ErrorCode MESSAGE_CONTENT_CONTAINS_SENSITIVE_WORDS = new IntErrorCode(4003_1_519, "消息内容包含敏感词");
    ErrorCode MESSAGE_TASK_ACCOUNT_NOT_DEFINED = new IntErrorCode(4003_1_520, "消息任务账户未定义");
    ErrorCode MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS = new IntErrorCode(4003_1_521, "消息渠道{}的账户不存在");
    ErrorCode CHANNEL_EMAIL_PROPERTY_NOT_CONFIG = new IntErrorCode(4003_1_522, "邮件渠道属性{}未配置");
    ErrorCode CHANNEL_ALIYUN_SMS_PROPERTY_NOT_CONFIG = new IntErrorCode(4003_1_523, "阿里云短信渠道属性{}未配置");
    ErrorCode CHANNEL_WX_MINI_PROGRAM_PROPERTY_NOT_CONFIG = new IntErrorCode(4003_1_524, "微信小程序渠道属性{}未配置");
    ErrorCode CHANNEL_WX_OFFICE_PROPERTY_NOT_CONFIG =  new IntErrorCode(4003_1_525, "微信公众号渠道属性{}未配置");
    ErrorCode DEALY_MESSAGE_TASK_NOT_DEFINE_SCHEDULE_TIME = new IntErrorCode(4003_1_527, "延时消息任务未定义执行时间");
    ErrorCode DEALY_MESSAGE_TASK_SCHEDULE_TIME_MUST_NOT_BEFORE_NOW = new IntErrorCode(4003_1_528, "延时消息任务执行时间必须在当前时间之后");
    ErrorCode CORN_MESSAGE_TASK_NOT_DEFINE_CORN = new IntErrorCode(4003_1_529, "定时消息任务未定义corn表达式");
    ErrorCode CORN_MESSAGE_TASK_EXPRESSION_INVALID = new IntErrorCode(4003_1_530, "定时消息任务corn表达式{}不合法");
    ErrorCode CORN_TASK_SUBMIT_FAILED = new IntErrorCode(4003_1_531, "定时消息任务提交失败,失败信息：{}");
    ErrorCode MESSAGE_TASK_NOT_EXISTS = new IntErrorCode(4003_1_532, "消息任务不存在");
    ErrorCode THIS_MESSAGE_TASK_NOT_SUPPORTS_CANCEL = new IntErrorCode(4003_1_533, "此任务不支持取消");
    ErrorCode DELAY_TASK_NOT_WAITING = new IntErrorCode(4003_1_534, "延时消息任务已执行，取消任务失败");
    ErrorCode ONLY_CORN_TASK_SUPPORTS_PAUSE = new IntErrorCode(4003_1_535, "仅定时消息任务支持暂停");
    ErrorCode MESSAGE_TASK_DETAIL_NOT_EXISTS = new IntErrorCode(4003_1_536, "消息任务详情不存在");
    ErrorCode THE_MESSAGE_CHANNEL_ACCOUNT_NOT_EXISTS = new IntErrorCode(4003_1_537, "消息渠道账户不存在");
    ErrorCode SEND_MESSAGE_TASK_TIMEOUT =  new IntErrorCode(4003_1_538, "发送消息任务超时");
    ErrorCode NOW_TASK_NOT_SUPPORTS_RESUME =  new IntErrorCode(4003_1_539, "立即消息任务不支持恢复");
    ErrorCode DEALY_MESSAGE_TASK_SCHEDULE_TIME_ALREADY_PASS =  new IntErrorCode(4003_1_540, "延时消息任务执行时间已过,操作失败");
    ErrorCode CLIENT_ACCESS_TOKEN_IS_INVALID =  new IntErrorCode(4003_1_541, "接入商token无效");
    ErrorCode CLIENT_ACCESS_TOKEN_IS_EXPIRED =  new IntErrorCode(4003_1_542, "接入商token已过期");
    ErrorCode CLIENT_ACCESS_COUNT_IS_NOT_ENOUGH =  new IntErrorCode(4003_1_543, "接入商配额不足");
    ErrorCode CLIENT_NOT_HAS_THIS_CHANNEL_PERMISSION =  new IntErrorCode(4003_1_544, "接入商没有此渠道的权限");
    ErrorCode PLAIN_TEXT_MESSAGE_MUST_NOT_EMPTY =  new IntErrorCode(4003_1_545, "纯文本消息的消息内容不能为空");
    ErrorCode SHIELD_TASK_NOT_WAITING =  new IntErrorCode(4003_1_546, "屏蔽消息任务已执行，取消任务失败");
    ErrorCode EXEC_MESSAGE_TASK_ERROR = new IntErrorCode(4003_1_547, "执行消息任务失败, 异常信息：{}");
    ErrorCode EXEC_MESSAGE_TASK_FAILED =  new IntErrorCode(4003_1_548, "执行消息任务失败, 任务处理器名称：{}");


    String XXL_THREAD_POOL_NAME = "execute-xxl-thread-pool";
    int MESSAGE_ACCESS_CLIENT_TOKEN_EXPIRED_TIME = 24 * 60 * 40 * 30; // 一个月
    String SEND_MESSAGE_TASK_CODE =  "sendMessageTask";
    int SEND_MESSAGE_TASK_STEP_ONE = 1;
    int SEND_MESSAGE_TASK_STEP_TWO = 2;
    int SEND_MESSAGE_TASK_STEP_THREE = 3;
    int SEND_MESSAGE_TASK_STEP_FOUR = 4;
}
