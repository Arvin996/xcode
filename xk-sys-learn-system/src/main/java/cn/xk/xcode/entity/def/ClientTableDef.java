package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-06-24
 */
public class ClientTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final ClientTableDef CLIENT_PO = new ClientTableDef();

    /**
     * 客户端id
     */
    public final QueryColumn CLIENT_ID = new QueryColumn(this, "client_id");

    /**
     * 客户端key
     */
    public final QueryColumn CLIENT_KEY = new QueryColumn(this, "client_key");

    /**
     * 授权类型
     */
    public final QueryColumn GRANT_TYPE = new QueryColumn(this, "grant_type");

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
     * 客户端秘钥
     */
    public final QueryColumn CLIENT_SECRET = new QueryColumn(this, "client_secret");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{CLIENT_ID, CLIENT_KEY, CLIENT_SECRET, GRANT_TYPE, CREATE_USER, CREATE_TIME, UPDATE_TIME};

    public ClientTableDef() {
        super("", "system_client");
    }

    private ClientTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ClientTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ClientTableDef("", "system_client", alias));
    }

}
