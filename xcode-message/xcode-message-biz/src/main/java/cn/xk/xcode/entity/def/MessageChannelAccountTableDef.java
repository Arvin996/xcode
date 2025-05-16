package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public class MessageChannelAccountTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final MessageChannelAccountTableDef MESSAGE_CHANNEL_ACCOUNT = new MessageChannelAccountTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 0 启用 1弃用
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 账号权重
     */
    public final QueryColumn WEIGHT = new QueryColumn(this, "weight");

    /**
     * 0 未删除 1已删除
     */
    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建用户
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 更新用户
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 渠道账户名称
     */
    public final QueryColumn ACCOUNT_NAME = new QueryColumn(this, "account_name");

    /**
     * 渠道code 
     */
    public final QueryColumn CHANNEL_ID = new QueryColumn(this, "channel_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ACCOUNT_NAME, CHANNEL_ID, WEIGHT, STATUS, IS_DELETED, CREATE_TIME, UPDATE_TIME, CREATE_USER, UPDATE_USER};

    public MessageChannelAccountTableDef() {
        super("", "message_channel_account");
    }

    private MessageChannelAccountTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MessageChannelAccountTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MessageChannelAccountTableDef("", "message_channel_account", alias));
    }

}
