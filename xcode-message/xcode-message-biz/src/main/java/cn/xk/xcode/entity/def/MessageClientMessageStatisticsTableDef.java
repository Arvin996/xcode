package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public class MessageClientMessageStatisticsTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MessageClientMessageStatisticsTableDef MESSAGE_CLIENT_MESSAGE_STATISTICS = new MessageClientMessageStatisticsTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 发送信息条数
     */
    public final QueryColumn COUNT = new QueryColumn(this, "count");

    /**
     * 渠道用户
     */
    public final QueryColumn CLIENT_ID = new QueryColumn(this, "client_id");

    /**
     * 失败条数
     */
    public final QueryColumn FAIL_COUNT = new QueryColumn(this, "fail_count");

    /**
     * 统计时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 渠道code
     */
    public final QueryColumn CHANNEL_ID = new QueryColumn(this, "channel_id");

    /**
     * 成功条数
     */
    public final QueryColumn SUCCESS_COUNT = new QueryColumn(this, "success_count");

    /**
     * 渠道发送账户
     */
    public final QueryColumn CHANNEL_ACCOUNT = new QueryColumn(this, "channel_account");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CLIENT_ID, CHANNEL_ID, CHANNEL_ACCOUNT, COUNT, SUCCESS_COUNT, FAIL_COUNT, CREATE_TIME};

    public MessageClientMessageStatisticsTableDef() {
        super("", "message_client_message_statistics");
    }

    private MessageClientMessageStatisticsTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MessageClientMessageStatisticsTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MessageClientMessageStatisticsTableDef("", "message_client_message_statistics", alias));
    }

}
