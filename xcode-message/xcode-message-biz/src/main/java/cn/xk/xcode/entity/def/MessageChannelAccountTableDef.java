package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 * 表定义层。
 *
 * @author xuk
 * @since 2025-03-10
 */
public class MessageChannelAccountTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final MessageChannelAccountTableDef MESSAGE_CHANNEL_ACCOUNT_PO = new MessageChannelAccountTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 权重
     */
    public final QueryColumn WEIGHT = new QueryColumn(this, "weight");

    /**
     * 0 启用 1弃用
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

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
    public final QueryColumn CHANNEL_CODE = new QueryColumn(this, "channel_code");

    /**
     * 渠道配置 jsonz字符串 主要配置appid等一些必须的参数
     */
    public final QueryColumn CHANNEL_CONFIG = new QueryColumn(this, "channel_config");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, WEIGHT, ACCOUNT_NAME, CHANNEL_CODE, CHANNEL_CONFIG, STATUS, CREATE_TIME, UPDATE_TIME, CREATE_USER, UPDATE_USER};

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
