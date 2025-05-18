package cn.xk.xcode.constants;

/**
 * @Author xuk
 * @Date 2025/3/10 11:35
 * @Version 1.0.0
 * @Description SendChannelUrlConstants
 **/
public class MessageChannelUrlConstant {

    /**
     * 个推相关的url
     */
    public static final String GE_TUI_BASE_URL = "https://restapi.getui.com/v2/";
    public static final String GE_TUI_SINGLE_PUSH_PATH = "/push/single/cid";
    public static final String GE_TUI_BATCH_PUSH_CREATE_TASK_PATH = "/push/list/message";
    public static final String GE_TUI_BATCH_PUSH_PATH = "/push/list/cid";
    public static final String GE_TUI_AUTH = "/auth";
    public static final String GE_TUI_TASK_DETAIL = "/task/detail/";

    /**
     * 钉钉工作消息相关的url
     */
    public static final String DING_DING_SEND_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2";
    public static final String DING_DING_RECALL_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/recall";
    public static final String DING_DING_PULL_URL = "https://oapi.dingtalk.com/topapi/message/corpconversation/getsendresult";
    public static final String DING_DING_UPLOAD_URL = "https://oapi.dingtalk.com/media/upload";
    public static final String DING_DING_TOKEN_URL = "https://oapi.dingtalk.com/gettoken";
    // =======================以上直接作废 以下开始
    public static final String WX_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    /**
     * 钉钉 webhook相关url
     */
    public static final String DING_DING_ROBOT_WEBHOOK_URL = "https://oapi.dingtalk.com/robot/send?access_token=%s";

    /**
     * feishu webhook相关url
     */
    public static final String FEI_SHU_ROBOT_WEBHOOK_URL = "https://open.feishu.cn/open-apis/bot/v2/hook/%s";

    /**
     * 微信小程序相关url
     */
    public static final String WX_MINI_PROGRAM_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=%s";
    /**
     * 微信公众号相关url
     */
    public static final String WX_OFFICIAL_ACCOUNT_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";

}
