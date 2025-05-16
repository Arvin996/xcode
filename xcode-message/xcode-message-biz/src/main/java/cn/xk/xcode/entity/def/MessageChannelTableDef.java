package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2025-05-15
 */
public class MessageChannelTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final MessageChannelTableDef MESSAGE_CHANNEL = new MessageChannelTableDef();


    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 渠道code 如短信sms 微信小程序等
     */
    public final QueryColumn CODE = new QueryColumn(this, "code");

    /**
     * 渠道名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 0 启动 1未启用
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
     * 是否支持负载均衡 0支持 1不支持
     */
    public final QueryColumn SUPPORT_LOAD_BALANCE = new QueryColumn(this, "support_load_balance");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CODE, NAME, SUPPORT_LOAD_BALANCE, STATUS, CREATE_TIME, UPDATE_TIME, CREATE_USER, UPDATE_USER};

    public MessageChannelTableDef() {
        super("", "message_channel");
    }

    private MessageChannelTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public MessageChannelTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new MessageChannelTableDef("", "message_channel", alias));
    }

}
