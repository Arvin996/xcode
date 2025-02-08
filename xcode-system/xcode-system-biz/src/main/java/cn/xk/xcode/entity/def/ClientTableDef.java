package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 * 表定义层。
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
     * 申请人标识
     */
    public final QueryColumn SUBJECT_ID = new QueryColumn(this, "subject_id");

    /**
     * 状态 0 可用 1不可用
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");


    /**
     * 允许的scope
     */
    public final QueryColumn SCOPES = new QueryColumn(this, "scopes");

    /**
     * 允许重定向的地址
     */
    public final QueryColumn REDIRECT_URIS = new QueryColumn(this, "redirect_uris");

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
     * 访问令牌过期时间
     */
    public final QueryColumn ACCESS_TOKEN_VALIDITY = new QueryColumn(this, "access_token_validity");

    /**
     * 刷新令牌过期时间
     */
    public final QueryColumn REFRESH_TOKEN_VALIDITY = new QueryColumn(this, "refresh_token_validity");


    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{CLIENT_ID, CLIENT_KEY, CLIENT_SECRET, SCOPES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY, SUBJECT_ID, REDIRECT_URIS, STATUS, GRANT_TYPE, CREATE_USER, CREATE_TIME, UPDATE_TIME};

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
