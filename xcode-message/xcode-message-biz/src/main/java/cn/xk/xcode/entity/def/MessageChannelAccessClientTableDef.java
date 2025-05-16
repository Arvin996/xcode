package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 * 表定义层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public class MessageChannelAccessClientTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final MessageChannelAccessClientTableDef MESSAGE_CHANNEL_ACCESS_CLIENT = new MessageChannelAccessClientTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 接入商名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 接入商邮箱
     */
    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    /**
     * 接入商手机号
     */
    public final QueryColumn MOBILE = new QueryColumn(this, "mobile");

    /**
     * 0启用 1禁用
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 是否删除 0未删除 1已删除
     */
    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * token刷新时间
     */
    public final QueryColumn TOKEN_REFRESH_TIME = new QueryColumn(this, "token_refresh_time");

    /**
     * 接入商消息配额 默认100
     */
    public final QueryColumn ACCESS_COUNT = new QueryColumn(this, "access_count");

    /**
     * 接入密钥，用于校验合法性
     */
    public final QueryColumn ACCESS_TOKEN = new QueryColumn(this, "access_token");

    /**
     * 剩余配额
     */
    public final QueryColumn REST_COUNT = new QueryColumn(this, "rest_count");

    /**
     * 已用配额
     */
    public final QueryColumn USED_COUNT = new QueryColumn(this, "used_count");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, NAME, EMAIL, MOBILE, ACCESS_TOKEN, TOKEN_REFRESH_TIME, IS_DELETED, STATUS, ACCESS_COUNT, REST_COUNT, USED_COUNT, CREATE_TIME, UPDATE_TIME};

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
