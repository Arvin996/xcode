package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author lenovo
 * @since 2024-06-25
 */
public class TableInfoTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TableInfoTableDef TABLE_INFO_PO = new TableInfoTableDef();

    /**
     * 表名前缀
     */
    public final QueryColumn TABLE_PRE = new QueryColumn(this, "table_pre");

    /**
     * 表名
     */
    public final QueryColumn TABLE_NAME = new QueryColumn(this, "table_name");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建用户
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 表所属数据库
     */
    public final QueryColumn DATABASE_ID = new QueryColumn(this, "database_id");

    /**
     * 实体类前缀
     */
    public final QueryColumn ENTITY_SUFF = new QueryColumn(this, "entity_suff");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{DATABASE_ID, TABLE_NAME, TABLE_PRE, ENTITY_SUFF, CREATE_USER, CREATE_TIME, UPDATE_TIME};

    public TableInfoTableDef() {
        super("", "infra_table_info");
    }

    private TableInfoTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TableInfoTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TableInfoTableDef("", "infra_table_info", alias));
    }

}
