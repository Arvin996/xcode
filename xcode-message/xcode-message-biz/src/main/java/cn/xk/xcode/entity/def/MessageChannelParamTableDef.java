package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 * 表定义层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public class MessageChannelParamTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final MessageChannelParamTableDef MESSAGE_CHANNEL_PARAM = new MessageChannelParamTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 参数描述
     */
    public final QueryColumn DESC = new QueryColumn(this, "desc");

    /**
     * 参数名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 是否必须 0否 1是
     */
    public final QueryColumn REQUIRED = new QueryColumn(this, "required");

    /**
     * 渠道id
     */
    public final QueryColumn CHANNEL_ID = new QueryColumn(this, "channel_id");

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
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 更新人
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CHANNEL_ID, NAME, REQUIRED, DESC, IS_DELETED, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER};

    public MessageChannelParamTableDef() {
        super("", "message_channel_param");
    }

    private MessageChannelParamTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MessageChannelParamTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MessageChannelParamTableDef("", "message_channel_param", alias));
    }

}
