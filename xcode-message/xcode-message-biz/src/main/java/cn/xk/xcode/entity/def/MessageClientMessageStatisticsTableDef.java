package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-03-10
 */
public class MessageClientMessageStatisticsTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MessageClientMessageStatisticsTableDef MESSAGE_CLIENT_MESSAGE_STATISTICS_PO = new MessageClientMessageStatisticsTableDef();

    
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
     * 统计时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 渠道code
     */
    public final QueryColumn CHANNEL_CODE = new QueryColumn(this, "channel_code");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CLIENT_ID, CHANNEL_CODE, COUNT, CREATE_TIME};

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
