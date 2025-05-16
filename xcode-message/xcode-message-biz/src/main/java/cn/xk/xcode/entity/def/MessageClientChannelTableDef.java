package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 * 表定义层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public class MessageClientChannelTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final MessageClientChannelTableDef MESSAGE_CLIENT_CHANNEL = new MessageClientChannelTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 接入商id
     */
    public final QueryColumn CLIENT_ID = new QueryColumn(this, "client_id");

    /**
     * 渠道
     */
    public final QueryColumn CHANNEL_ID = new QueryColumn(this, "channel_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CLIENT_ID, CHANNEL_ID};

    public MessageClientChannelTableDef() {
        super("", "message_client_channel");
    }

    private MessageClientChannelTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MessageClientChannelTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MessageClientChannelTableDef("", "message_client_channel", alias));
    }

}
