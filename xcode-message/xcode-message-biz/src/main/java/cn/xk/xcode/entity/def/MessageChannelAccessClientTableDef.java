package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author xuk
 * @since 2025-03-10
 */
public class MessageChannelAccessClientTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MessageChannelAccessClientTableDef MESSAGE_CHANNEL_ACCESS_CLIENT_PO = new MessageChannelAccessClientTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 接入商名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 0启用 1禁用
     */
    public final QueryColumn STATE = new QueryColumn(this, "state");

    /**
     * 令牌发送时间
     */
    public final QueryColumn ACCESS_TIME = new QueryColumn(this, "access_time");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 接入密钥，用于校验合法性
     */
    public final QueryColumn ACCESS_TOKEN = new QueryColumn(this, "access_token");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, ACCESS_TOKEN, ACCESS_TIME, STATE, CREATE_TIME, UPDATE_TIME};

    public MessageChannelAccessClientTableDef() {
        super("", "message_channel_access_client");
    }

    private MessageChannelAccessClientTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MessageChannelAccessClientTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MessageChannelAccessClientTableDef("", "message_channel_access_client", alias));
    }

}
