package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 * 表定义层。
 *
 * @author xuk
 * @since 2025-03-10
 */
public class MessageTaskTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final MessageTaskTableDef MESSAGE_TASK_PO = new MessageTaskTableDef();


    public final QueryColumn ID = new QueryColumn(this, "id");


    /**
     * 渠道账号id
     */
    public final QueryColumn ACCOUNT_ID = new QueryColumn(this, "account_id");

    /**
     * 00 待发送 10 部分发送成功 20 发送失败 30 全部发送成功 40 取消
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 定时任务corn
     */
    public final QueryColumn MSG_CORN = new QueryColumn(this, "msg_corn");

    /**
     * 消息类型 delay 延时发送 几点发送 now立即发送 corn 定时发送
     */
    public final QueryColumn MSG_TYPE = new QueryColumn(this, "msg_type");

    /**
     * 接入商id
     */
    public final QueryColumn CLIENT_ID = new QueryColumn(this, "client_id");

    /**
     * 发送类型 如短信 微信公众号等
     */
    public final QueryColumn SEND_TYPE = new QueryColumn(this, "send_type");

    /**
     * 接收人类型 00 文本用逗号隔开 10 csv文件
     */
    public final QueryColumn RECEIVER_TYPE = new QueryColumn(this, "receiver_type");

    /**
     * 接收人 json格式
     */
    public final QueryColumn RECEIVERS = new QueryColumn(this, "receivers");

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
     * 执行时间 针对延时发送而言
     */
    public final QueryColumn SCHEDULE_TIME = new QueryColumn(this, "schedule_time");

    /**
     * 结束屏蔽时间
     */
    public final QueryColumn SHIELD_END_TIME = new QueryColumn(this, "shield_end_time");

    /**
     * 消息内容
     */
    public final QueryColumn MESSAGE_CONTENT = new QueryColumn(this, "message_content");

    /**
     * 消息内容类型 plain 文本消息 立即发送 template 模板消息
     */
    public final QueryColumn MSG_CONTENT_TYPE = new QueryColumn(this, "msg_content_type");

    /**
     * 开始屏蔽时间
     */
    public final QueryColumn SHIELD_START_TIME = new QueryColumn(this, "shield_start_time");

    /**
     * 模板参数值
     */
    public final QueryColumn CONTENT_VALUE_PARAMS = new QueryColumn(this, "content_value_params");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CLIENT_ID, ACCOUNT_ID, RECEIVER_TYPE, SHIELD_TYPE, SHIELD_START_TIME, SHIELD_END_TIME, MSG_TYPE, SEND_TYPE, MSG_CORN, TASK_CORN_ID, SCHEDULE_TIME, MSG_CONTENT_TYPE, TEMPLATE_ID, MESSAGE_CONTENT, CONTENT_VALUE_PARAMS, RECEIVERS, STATUS};

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
