package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public class MessageTaskTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final MessageTaskTableDef MESSAGE_TASK = new MessageTaskTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 00 待发送 
     10 部分发送成功
     20 全部发送失败
     30 全部发送成功
     40 取消发送（延时任务）
     50 暂停发送（定时任务）
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 消息类型 delay 延时发送 now立即发送 corn 定时发送
     */
    public final QueryColumn MSG_TYPE = new QueryColumn(this, "msg_type");

    /**
     * 接入商id
     */
    public final QueryColumn CLIENT_ID = new QueryColumn(this, "client_id");

    /**
     * 如果为定时任务 则是定时任务corn
     */
    public final QueryColumn TASK_CORN = new QueryColumn(this, "task_corn");

    /**
     * 渠道账号id
     */
    public final QueryColumn ACCOUNT_ID = new QueryColumn(this, "account_id");

    /**
     * 任务创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 消息屏蔽类型 00不屏蔽 10 夜间屏蔽 20时间区间屏蔽
     */
    public final QueryColumn SHIELD_TYPE = new QueryColumn(this, "shield_type");

    /**
     * xxl中的任务id
     */
    public final QueryColumn TASK_CORN_ID = new QueryColumn(this, "task_corn_id");

    /**
     * 模板id
     */
    public final QueryColumn TEMPLATE_ID = new QueryColumn(this, "template_id");

    /**
     * 发送渠道 如短信 微信公众号等
     */
    public final QueryColumn CHANNEL_ID = new QueryColumn(this, "channel_id");

    /**
     * 任务执行时间
     */
    public final QueryColumn TRIGGER_TIME = new QueryColumn(this, "trigger_time");

    /**
     * 接收人类型 00透传直接发送 10 csv文件
     */
    public final QueryColumn RECEIVER_TYPE = new QueryColumn(this, "receiver_type");

    /**
     * 接收人
     */
    public final QueryColumn RECEIVERS = new QueryColumn(this, "receivers");

    /**
     * 执行时间 针对延时发送而言
     */
    public final QueryColumn SCHEDULE_TIME = new QueryColumn(this, "schedule_time");

    /**
     * 结束屏蔽时间 1700
     */
    public final QueryColumn SHIELD_END_TIME = new QueryColumn(this, "shield_end_time");

    /**
     * 消息内容
     */
    public final QueryColumn MESSAGE_CONTENT = new QueryColumn(this, "message_content");

    /**
     * 消息内容类型 plain文本 template 模板消息
     */
    public final QueryColumn MSG_CONTENT_TYPE = new QueryColumn(this, "msg_content_type");

    /**
     * 开始屏蔽时间 0800
     */
    public final QueryColumn SHIELD_START_TIME = new QueryColumn(this, "shield_start_time");

    /**
     * 模板参数值 json格式
     */
    public final QueryColumn CONTENT_VALUE_PARAMS = new QueryColumn(this, "content_value_params");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CLIENT_ID, ACCOUNT_ID, SHIELD_TYPE, SHIELD_START_TIME, SHIELD_END_TIME, MSG_TYPE, CHANNEL_ID, TASK_CORN, TASK_CORN_ID, SCHEDULE_TIME, MSG_CONTENT_TYPE, TEMPLATE_ID, MESSAGE_CONTENT, CONTENT_VALUE_PARAMS, RECEIVER_TYPE, STATUS, CREATE_TIME, TRIGGER_TIME};

    public MessageTaskTableDef() {
        super("", "message_task");
    }

    private MessageTaskTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MessageTaskTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MessageTaskTableDef("", "message_task", alias));
    }

}
