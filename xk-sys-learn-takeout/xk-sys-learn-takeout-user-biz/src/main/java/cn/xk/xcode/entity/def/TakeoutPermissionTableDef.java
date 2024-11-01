package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 *  表定义层。
 *
 * @author Administrator
 * @since 2024-10-29
 */
public class TakeoutPermissionTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public static final TakeoutPermissionTableDef TAKEOUT_PERMISSION_PO = new TakeoutPermissionTableDef();

    /**
     * 自增id
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 资源名
     */
    public final QueryColumn CODE = new QueryColumn(this, "code");

    /**
     * 是否删除 0否 1是
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
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, CODE, CREATE_USER, UPDATE_USER, CREATE_TIME, UPDATE_TIME, IS_DELETED};

    public TakeoutPermissionTableDef() {
        super("", "takeout_permission");
    }

    private TakeoutPermissionTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public TakeoutPermissionTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new TakeoutPermissionTableDef("", "takeout_permission", alias));
    }

}
