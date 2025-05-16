package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 * 表定义层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public class MessageChannelAccountParamValueTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final MessageChannelAccountParamValueTableDef MESSAGE_CHANNEL_ACCOUNT_PARAM_VALUE = new MessageChannelAccountParamValueTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 账号id
     */
    public final QueryColumn ACCOUNT_ID = new QueryColumn(this, "account_id");

    /**
     * 是否删除 0未删除 1已删除
     */
    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 参数值
     */
    public final QueryColumn PARAM_VALUE = new QueryColumn(this, "param_value");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 更新人
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 渠道参数id
     */
    public final QueryColumn CHANNEL_PARAM_ID = new QueryColumn(this, "channel_param_id");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ACCOUNT_ID, CHANNEL_PARAM_ID, PARAM_VALUE, IS_DELETED, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER};

    public MessageChannelAccountParamValueTableDef() {
        super("", "message_channel_account_param_value");
    }

    private MessageChannelAccountParamValueTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MessageChannelAccountParamValueTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MessageChannelAccountParamValueTableDef("", "message_channel_account_param_value", alias));
    }

}
