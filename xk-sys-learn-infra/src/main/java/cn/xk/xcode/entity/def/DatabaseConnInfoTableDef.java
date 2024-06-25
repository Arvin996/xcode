package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-06-25
 */
public class DatabaseConnInfoTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final DatabaseConnInfoTableDef DATABASE_CONN_INFO_PO = new DatabaseConnInfoTableDef();

    /**
     * 自增主键
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 数据库连接地址 ip:port 只支持mysql
     */
    public final QueryColumn URL = new QueryColumn(this, "url");

    /**
     * 数据库密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 数据库用户名
     */
    public final QueryColumn USER_NAME = new QueryColumn(this, "user_name");

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
     * 数据库名
     */
    public final QueryColumn DATABASE_NAME = new QueryColumn(this, "database_name");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, DATABASE_NAME, URL, USER_NAME, PASSWORD, CREATE_USER, CREATE_TIME, UPDATE_TIME};

    public DatabaseConnInfoTableDef() {
        super("", "infra_database_conn_info");
    }

    private DatabaseConnInfoTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public DatabaseConnInfoTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new DatabaseConnInfoTableDef("", "infra_database_conn_info", alias));
    }

}
